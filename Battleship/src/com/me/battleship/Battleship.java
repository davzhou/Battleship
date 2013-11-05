package com.me.battleship;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;

public class Battleship implements ApplicationListener, InputProcessor {
    private Torpedo t;// test

    private Board player1;

    private Ship beingDragged;
    private float timeDragged;
    private boolean rotated;
    private Drawer drawer;

    @Override
    public void create() {
        timeDragged = 0f;
        rotated = false;
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
        drawer.draw(beingDragged);
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
        boolean onGrid = Globals.insideRegion(x, y, Globals.GridTopLeft,
                new Vector2(Globals.GridSize, Globals.GridSize));
        if (beingDragged != null && timeDragged > .15f && onGrid) {
            beingDragged.locationSet = true;
            player1.centerShipOnGrid(beingDragged);
        } else if (beingDragged != null) {
            beingDragged.reset();
        } else {
            for (int i = 0; i < player1.ships.size(); i++)
                if (touchedShip(player1.ships.get(i), x, y)) {
                    player1.ships.get(i).reset();
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
                    return true;
                }
        } else {
            timeDragged += Gdx.graphics.getDeltaTime();
            beingDragged.move(x, y);
            if (!rotated
                    && Globals.insideRegion(x, y, Globals.RotateZoneTopLeft,
                            Globals.RotateZoneSize)) {
                rotated = true;
                beingDragged.changeOrientation();
            } else if (rotated
                    && !Globals.insideRegion(x, y, Globals.RotateZoneTopLeft,
                            Globals.RotateZoneSize))
                rotated = false;
            player1.highlightSquares(x, y, beingDragged);
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
        return Globals.insideRegion(x, y, o.TopLeft, o.Size);
    }
}
