package com.me.battleship;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.GL10;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class Drawer {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ArrayList<Sprite> sprites;
    private Board boards[];
    private Properties props = new Properties();
    private int screenWidth, screenHeight, numPlayers;
    private Texture tileTexture, selectedTileTexture[], cursorTexture, hit, miss;
    private TextureRegion[] cruiser, patrol, battleship, submarine, carrier, arrows;
    private Button rotateRegion, autoButton, readyButton, turnArrow;

    public Drawer(Board boards[], Button rotateRegion, Button autoButton, Button readyButton, Button turnArrow,
            int numPlayers) {
        try {
            props.load(Gdx.files.internal("data/config.properties").read());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(10);
        }
        this.boards = boards;
        this.rotateRegion = rotateRegion;
        this.autoButton = autoButton;
        this.readyButton = readyButton;
        this.turnArrow = turnArrow;
        this.numPlayers = numPlayers;
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Set it to an orthographic projection with "y down"
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        camera.setToOrtho(true, screenWidth, screenHeight);
        camera.update();

        // Create a full screen sprite renderer and use the above camera
        batch = new SpriteBatch(screenHeight, screenHeight);
        batch.setProjectionMatrix(camera.combined);
        sprites = new ArrayList<Sprite>();

        loadTextures();
    }

    private Sprite createSprite(String texturePath, int width, int height) {
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        TextureRegion region = new TextureRegion(texture, width, height);
        return new Sprite(region);

    }

    public void drawSetup(Ship selectedShip) {

        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        // for (Sprite sprite : sprites) { sprite.draw(batch); }

        drawGrid(boards[0]);
        // draw rotate zone
        batch.draw(tileTexture, rotateRegion.topLeft.x, rotateRegion.topLeft.y, rotateRegion.dimensions.x,
                rotateRegion.dimensions.y);
        // draw auto button
        batch.draw(tileTexture, autoButton.topLeft.x, autoButton.topLeft.y, autoButton.dimensions.x,
                autoButton.dimensions.y);
        // draw ready button
        batch.draw(tileTexture, readyButton.topLeft.x, readyButton.topLeft.y, readyButton.dimensions.x,
                readyButton.dimensions.y);
        // draw highlighted squares
        int tileSize = boards[0].getTileSize();
        for (int j = 0; j < boards[0].getActiveSquares().length; j++) {
            int which_texture = boards[0].validShipPlacement ? 1 : 0;
            if (boards[0].getActiveSquares()[j]) {
                batch.draw(selectedTileTexture[which_texture], boards[0].getOnSquares()[j].x * tileSize
                        + boards[0].topLeft.x, boards[0].getOnSquares()[j].y * tileSize + boards[0].topLeft.y,
                        tileSize, tileSize);
            }
        }

        drawShips(boards[0]);

        if (selectedShip != null) {
            TextureRegion shipTexture;
            if (boards[0].validShipPlacement) {
                shipTexture = getShipTexture(selectedShip, 0);
            } else {
                shipTexture = getShipTexture(selectedShip, 1);
            }
            drawShip(selectedShip, shipTexture);
        }
        batch.end();
    }

    public void drawGame(int turns) {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        drawOwnBoard(boards[(turns + 1) % numPlayers]);
        drawEnemyBoard(boards[turns % numPlayers]);

        drawOther(turns);

        batch.end();
    }

    public void drawGameOver(int turns) {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        drawOwnBoard(boards[(turns + 1) % numPlayers]);
        drawOwnBoard(boards[turns % numPlayers]);

        batch.end();
    }

    public void dispose() {
        batch.dispose();
    }

    private void loadTextures() {

        // background
        /*
         * Sprite bg = createSprite(props.getProperty("texture.background"),
         * 1280, 720); bg.setPosition(0, 0); sprites.add(bg);
         */

        // create border
        // Sprite p1GridBorder =
        // createSprite("data/boardLeft/large_teal_border.png", 512, 512);
        // p1GridBorder.setSize(screenHeight * .75f, screenHeight * .75f);
        // bgSprite.setOrigin(bgSprite.getWidth() / 2, bgSprite.getHeight() /
        // 2);
        // bgSprite.setPosition(-bgSprite.getWidth()/2,
        // -bgSprite.getHeight()/2);
        // p1GridBorder.setPosition(25, 100);
        // sprites.add(p1GridBorder);
        // Sprite p2GridBorder = new Sprite(p1GridBorder);
        // p2GridBorder.setPosition(screenWidth - p1GridBorder.getX() -
        // p2GridBorder.getWidth(), p1GridBorder.getY());
        // sprites.add(p2GridBorder);

        Texture shipTextures;
        shipTextures = new Texture(Gdx.files.internal(props.getProperty("texture.ships")));
        patrol = textureFlipper(new TextureRegion(shipTextures, 0, 0, 52, 32), new TextureRegion(shipTextures, 0, 200,
                52, 32));
        cruiser = textureFlipper(new TextureRegion(shipTextures, 0, 40, 78, 28), new TextureRegion(shipTextures, 0,
                240, 78, 28));
        submarine = textureFlipper(new TextureRegion(shipTextures, 0, 80, 91, 25), new TextureRegion(shipTextures, 0,
                280, 91, 25));
        battleship = textureFlipper(new TextureRegion(shipTextures, 0, 120, 116, 27), new TextureRegion(shipTextures,
                0, 320, 116, 27));
        carrier = textureFlipper(new TextureRegion(shipTextures, 0, 160, 123, 31), new TextureRegion(shipTextures, 0,
                360, 123, 31));
        // TODO

        tileTexture = new Texture(Gdx.files.internal(props.getProperty("texture.tile.empty")));
        selectedTileTexture = new Texture[2];
        selectedTileTexture[0] = new Texture(Gdx.files.internal(props.getProperty("texture.tile.invalid")));
        selectedTileTexture[1] = new Texture(Gdx.files.internal(props.getProperty("texture.tile.valid")));

        cursorTexture = new Texture(Gdx.files.internal(props.getProperty("texture.cursor")));
        hit = new Texture(Gdx.files.internal(props.getProperty("texture.hit")));
        miss = new Texture(Gdx.files.internal(props.getProperty("texture.miss")));

        Texture arrowTextures;
        arrowTextures = new Texture(Gdx.files.internal(props.getProperty("texture.arrow")));
        arrows = textureFlipper(new TextureRegion(arrowTextures, 0, 0, 53, 53), new TextureRegion(arrowTextures, 0, 53,
                53, 53));
    }

    private static TextureRegion[] textureFlipper(TextureRegion... textures) {
        for (int i = 0; i < textures.length; i++)
            textures[i].flip(false, true);
        return textures;
    }

    private static TextureRegion textureFlipper(TextureRegion texture) {
        texture.flip(false, true);
        return texture;
    }

    private void drawGrid(Board board) {
        int tileSize = board.getTileSize();
        for (int x = 0; x < board.getSize(); x++) {
            for (int y = 0; y < board.getSize(); y++) {
                batch.draw(tileTexture, x * tileSize + board.topLeft.x, y * tileSize + board.topLeft.y, tileSize,
                        tileSize);
            }
        }
    }

    private void drawOwnBoard(Board board) {
        drawGrid(board);
        drawShips(board);
        drawAttacks(board);
    }

    private void drawEnemyBoard(Board board) {
        drawGrid(board);
        drawSunkShips(board);
        drawAttacks(board);
        drawCursor(board);
    }

    private void drawShips(Board board) {
        for (Ship ship : board.getShips()) {
            drawShip(ship, getShipTexture(ship, 0));
        }

    }

    private void drawSunkShips(Board board) {
        for (Ship ship : board.getShips()) {
            if (ship.isSunk()) {
                drawShip(ship, getShipTexture(ship, 0));
            }
        }
    }

    private TextureRegion getShipTexture(Ship s, int color) {
        switch (s.getShipClass()) {
        // TODO
        case PATROL:
            return patrol[color];
        case CRUISER:
            return cruiser[color];
        case SUBMARINE:
            return submarine[color];
        case BATTLESHIP:
            return battleship[color];
        case CARRIER:
        default:
            return carrier[color];
        }
    }

    private void drawShip(Ship ship, TextureRegion shipTexture) {
        switch (ship.getOrientation()) {
        case HORIZONTAL:
            batch.draw(shipTexture, ship.topLeft.x, ship.topLeft.y, ship.dimensions.x, ship.dimensions.y);
            break;
        case VERTICAL:
        default:
            Vector2 o_dim = ship.getOrigDimensions();
            batch.draw(shipTexture, ship.center.x - o_dim.x / 2, ship.center.y - o_dim.y / 2, o_dim.x / 2, o_dim.y / 2,
                    o_dim.x, o_dim.y, 1f, 1f, 90f);
            break;
        }
    }

    private void drawOther(int turns) {
        batch.draw(arrows[turns % 2], turnArrow.topLeft.x, turnArrow.topLeft.y, turnArrow.dimensions.x,
                turnArrow.dimensions.y);
    }

    private void drawAttacks(Board board) {
        int tileSize = board.getTileSize();

        for (int i = 0; i < board.getFillGrid().length; i++) {
            for (int j = 0; j < board.getFillGrid()[i].length; j++) {
                if (board.getFillGrid()[i][j] < 0) {
                    if (board.getFillGrid()[i][j] == -Globals.EMPTY) {
                        batch.draw(miss, tileSize * i + board.topLeft.x, tileSize * j + board.topLeft.y, tileSize,
                                tileSize);
                    } else {
                        batch.draw(hit, tileSize * i + board.topLeft.x, tileSize * j + board.topLeft.y, tileSize,
                                tileSize);
                    }
                }
            }
        }
    }

    private void drawCursor(Board board) {
        int tileSize = board.getTileSize();

        for (int j = 0; j < board.getActiveSquares().length; j++) {
            if (board.getActiveSquares()[j]) {
                batch.draw(cursorTexture, board.getOnSquares()[j].x, board.getOnSquares()[j].y, tileSize * 3,
                        tileSize * 3);
            }
        }
    }
}
