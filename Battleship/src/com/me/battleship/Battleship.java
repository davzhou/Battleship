package com.me.battleship;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;

public class Battleship implements ApplicationListener, InputProcessor {
    private Torpedo t;// test

    private Board player1;

    private Ship beingDragged;
    private float timeDragged;
    private boolean rotated;
    private Drawer drawer;
    private int[] tracker;

    @Override
    public void create() {
        timeDragged = 0f;
        rotated = false;
        tracker = new int[] { 0, 0 };
        t = new Torpedo(50f, 50f, Globals.AttackStatus.HIT);

        player1 = new Board("player1", Globals.GridDimensions);
        drawer = new Drawer(player1);
        Globals.Loader();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void dispose() {
        drawer.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        drawer.draw();
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
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        boolean onGrid = x > Globals.GridLocation.x
                && x < Globals.GridLocation.x + Globals.GridSize
                && y > Globals.GridLocation.y
                && y < Globals.GridLocation.y + Globals.GridSize;
        if (beingDragged != null && timeDragged > .15f && onGrid) {
            beingDragged.locationSet = true;
        } else if (beingDragged != null && onGrid) {
            int temp_x, temp_y;
            temp_x = (int) ((x - Globals.GridLocation.x) / (Globals.GridSize / Globals.GridDimensions));
            temp_y = (int) ((y - Globals.GridLocation.y) / (Globals.GridSize / Globals.GridDimensions));
            player1.selectedSquares[temp_x][temp_y] = false;
            beingDragged.resetLocation();
        } else if (beingDragged != null) {
            beingDragged.resetLocation();
        } else {
            for (int i = 0; i < player1.ships.size(); i++)
                if (touchedShip(player1.ships.get(i), x, y)) {
                    player1.ships.get(i).resetLocation();
                    return true;
                }
        }
        beingDragged = null;
        timeDragged = 0f;
        return true;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        if (beingDragged == null) {
            for (int i = 0; i < player1.ships.size(); i++)
                if (touchedShip(player1.ships.get(i), x, y)) {
                    beingDragged = player1.ships.get(i);
                    player1.ships.get(i).x = x - player1.ships.get(i).width / 2;
                    player1.ships.get(i).y = y - player1.ships.get(i).height
                            / 2;

                    return true;
                }
        } else {
            timeDragged += Gdx.graphics.getDeltaTime();
            beingDragged.x = x - beingDragged.width / 2;
            beingDragged.y = y - beingDragged.height / 2;
            if (!rotated
                    && beingDragged.x > Globals.RotateZoneLocation.x
                    && beingDragged.x < Globals.RotateZoneLocation.x
                            + Globals.RotateZoneSize.x
                    && beingDragged.y > Globals.RotateZoneLocation.y
                    && beingDragged.y < Globals.RotateZoneLocation.y
                            + Globals.RotateZoneSize.y) {
                rotated = true;
                beingDragged.orientation = (beingDragged.orientation + 1) % 2;
            } else if (rotated
                    && (beingDragged.x < Globals.RotateZoneLocation.x
                            || beingDragged.x > Globals.RotateZoneLocation.x
                                    + Globals.RotateZoneSize.x
                            || beingDragged.y < Globals.RotateZoneLocation.y || beingDragged.y > Globals.RotateZoneLocation.y
                            + Globals.RotateZoneSize.y))
                rotated = false;
            else if (x > Globals.GridLocation.x
                    && x < Globals.GridLocation.x + Globals.GridSize
                    && y > Globals.GridLocation.y
                    && y < Globals.GridLocation.y + Globals.GridSize) {
                int temp_x, temp_y;
                temp_x = (int) ((x - Globals.GridLocation.x) / (Globals.GridSize / Globals.GridDimensions));
                temp_y = (int) ((y - Globals.GridLocation.y) / (Globals.GridSize / Globals.GridDimensions));
                if (temp_x != tracker[0] || temp_y != tracker[1]) {
                    player1.selectedSquares[tracker[0]][tracker[1]] = false;
                }
                player1.selectedSquares[temp_x][temp_y] = true;
                tracker[0] = temp_x;
                tracker[1] = temp_y;
            } else {
                player1.selectedSquares[tracker[0]][tracker[1]] = false;
            }

        }
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    boolean touchedShip(Ship o, int x, int y) {
        if (x > o.x && x < o.x + o.width && y > o.y && y < o.y + o.height) {
            return true;
        }
        return false;
    }
}
