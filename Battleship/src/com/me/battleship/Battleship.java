package com.me.battleship;

import com.badlogic.gdx.Game;


public class Battleship extends Game {

    private StartScreen startScreen;


    private Ship selectedShip;
    private float timeDragged;
    private boolean rotated;
    private int turns, numPlayers;
    private Drawer drawer;
    private Properties props = new Properties();
    private Button rotateRegion, autoButton, readyButton, turnArrow;
    private GameState gameState;

    public enum GameState {
        MENU, SETUP, GAME, GAMEOVER;
    }

    @Override
    public void create() {
        Gdx.input.setInputProcessor(this);
        startNewGame();
    }

    public void startNewGame() {
        try {
            props.load(Gdx.files.internal("data/config.properties").read());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(10);
        }
        gameState = GameState.SETUP;
        timeDragged = 0f;
        rotated = false;
        numPlayers = Integer.valueOf(props.getProperty("player.numbers"));
        int gridSize = Integer.parseInt(props.getProperty("grid.size"));
        players = new Board[numPlayers];
        players[0] = new Board(Integer.valueOf(props.getProperty("grid.left.loc.x")), Integer.valueOf(props
                .getProperty("grid.left.loc.y")), Integer.valueOf(props.getProperty("grid.dimensions.x")),
                Integer.valueOf(props.getProperty("grid.dimensions.y")), "player1", gridSize, true);
        players[1] = new Board(Integer.valueOf(props.getProperty("grid.right.loc.x")), Integer.valueOf(props
                .getProperty("grid.right.loc.y")), Integer.valueOf(props.getProperty("grid.dimensions.x")),
                Integer.valueOf(props.getProperty("grid.dimensions.y")), "player2", gridSize, false);
        rotateRegion = initializeButton("rotate.zone");
        autoButton = initializeButton("auto.button");
        readyButton = initializeButton("ready.button");
        turnArrow = initializeButton("arrow.button");
        drawer = new Drawer(players, rotateRegion, autoButton, readyButton, turnArrow, numPlayers);
        createShips(players[0]);
        createShips(players[1]);
        turns=0;
    }

    @Override
    public void dispose() {
        drawer.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);


        switch (gameState) {
            case SETUP:
                drawer.drawSetup(selectedShip);
                break;
            case GAME:
                Board p = players[(turns) % numPlayers];
                if (p.isGameOver()) {
                    gameState = GameState.GAMEOVER;
                    break;
                }
                if (p.isAI()) {
                    if(p.attackLocation()) {
                        turns++;
                    }
                }
                drawer.drawGame(turns);
                break;
            case GAMEOVER:
            default:
                drawer.drawGameOver(turns);
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        /*
         * if (Globals.isInside(x, y, player2)) {
         * player2.selectSquare(x, y);
         * }
         * return true;
         */
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {

        switch (gameState) {
            case SETUP:
                if (selectedShip != null && players[0].validShipPlacement && timeDragged > .1f) {
                    players[0].centerShipOnSquare(selectedShip);
                    players[0].placeShipOnGrid(selectedShip);
                } else if (selectedShip != null) {
                    players[0].removeShipIfOnGrid(selectedShip);
                    selectedShip.reset();
                } else if (selectedShip == null && Globals.isInside(x, y, autoButton)) {
                    players[0].autoPlace();
                } else if (selectedShip == null && Globals.isInside(x, y, readyButton) && players[0].isAllPlaced()) {
                    players[1].autoPlace();
                    gameState = GameState.GAME;
                } else {
                    for (int i = 0; i < players[0].ships.size(); i++)
                        if (touchedShip(players[0].ships.get(i), x, y)) {
                            players[0].removeShipIfOnGrid(players[0].ships.get(i));
                            players[0].ships.get(i).reset();
                            return true;
                        }
                }
                players[0].deselectSquares();
                selectedShip = null;
                timeDragged = 0f;
                break;
            case GAME:
                players[turns%numPlayers].deselectSquares();
                if (Globals.isInside(x, y, players[turns%numPlayers])) {
                    if (players[turns%numPlayers].attackLocation(x, y)){
                        turns++;
                    }
                }
                break;
            case GAMEOVER:
            default:
                startNewGame();
        }
        return true;

    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        switch (gameState) {
            case SETUP:
                if (selectedShip == null) {
                    for (Ship ship : players[0].getShips()) {
                        if (touchedShip(ship, x, y)) {
                            selectedShip = ship;
                            players[0].removeShipIfOnGrid(selectedShip);
                            return true;
                        }
                    }
                } else {
                    timeDragged += Gdx.graphics.getDeltaTime();
                    selectedShip.move(x, y);
                    if (!rotated && Globals.isInside(x, y, rotateRegion)) {
                        rotated = true;
                        selectedShip.changeOrientation();
                    } else if (rotated && !Globals.isInside(x, y, rotateRegion)) {
                        rotated = false;
                    }
                    players[0].identifySquares(x, y, selectedShip);
                }
                break;
            case GAME:
                if (Globals.isInside(x, y, players[turns%2])) {
                    players[turns%2].selectSquare(x, y);
                } else {
                    players[turns%2].deselectSquares();
                }
                break;
            default:
        }
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    boolean touchedShip(Ship s, int x, int y) {
        return Globals.isInside(x, y, s);
    }

    private void createShips(Board player) {
        String[] shipSizes = props.getProperty("grid.ships").split(",");
        for (int i = 0; i < shipSizes.length; i++) {
            player.addShip(new Ship(Integer.valueOf(props.getProperty("ship.zone.loc.x")), i * player.getTileSize()
                    + Integer.valueOf(props.getProperty("ship.zone.loc.y")),
                    Ship.ShipClass.valueOf(shipSizes[i].trim()), Ship.Orientation.HORIZONTAL, player.getTileSize()));
        }
    }

    private Button initializeButton(String name){
        return new Button(Integer.valueOf(props.getProperty(name+".loc.x")), Integer.valueOf(props
                .getProperty(name+".loc.y")), Integer.valueOf(props.getProperty(name+".size.x")),
                Integer.valueOf(props.getProperty(name+".size.y")));
    }

}
