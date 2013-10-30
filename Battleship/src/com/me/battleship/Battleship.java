package com.me.battleship;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Battleship implements ApplicationListener, InputProcessor {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture background;
    private Texture board;
    private Torpedo t;// test

    private Sprite bg_sprite;
    private Sprite board_sprite;

    private Board Player1;

    private Ship beingDragged;
    private float timeDragged;

    @Override
    public void create() {
        timeDragged = 0f;
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight());
        // Set it to an orthographic projection with "y down" (the first boolean
        // parameter)
        camera.setToOrtho(true, Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight());
        camera.update();

        // Create a full screen sprite renderer and use the above camera
        batch = new SpriteBatch(Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight());
        batch.setProjectionMatrix(camera.combined);

        /*
         * background = new
         * Texture(Gdx.files.internal("data/board/sea_bg.png"));
         * background.setFilter(TextureFilter.Linear, TextureFilter.Linear);
         */
        board = new Texture(Gdx.files.internal("data/board/grid_2.png"));

        t = new Torpedo(50f, 50f, Globals.AttackStatus.HIT);

        /*
         * TextureRegion bg_region = new TextureRegion(background, 0, 0, 1024,
         * 768);
         */

        /*
         * bg_sprite = new Sprite(bg_region); bg_sprite.setSize(1.0f, 1.0f *
         * bg_sprite.getHeight() / bg_sprite.getWidth());
         * bg_sprite.setOrigin(bg_sprite.getWidth()/2, bg_sprite.getHeight()/2);
         * bg_sprite.setPosition(-bg_sprite.getWidth()/2,
         * -bg_sprite.getHeight()/2);
         */

        /*
         * board_sprite = new Sprite(board_region); board_sprite.setSize(.7f,
         * .7f * board_sprite.getHeight() / board_sprite.getWidth());
         * board_sprite.setSize(.6f, .6f);
         * board_sprite.setOrigin(bg_sprite.getWidth() / 2,
         * bg_sprite.getHeight() / 2); board_sprite.setOrigin(0, 0);
         * board_sprite.setPosition(-board_sprite.getWidth() * 3 / 4,
         * -board_sprite.getHeight() / 2);
         */

        Player1 = new Board("Player1");

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(board, Globals.GridLocation1.x, Globals.GridLocation1.y,
                Globals.GridSize1.x, Globals.GridSize1.y);
        for (int i = 0; i < Player1.ships.size(); i++)
            batch.draw(Player1.ships.get(i).tex, Player1.ships.get(i).x,
                    Player1.ships.get(i).y, Player1.ships.get(i).width,
                    Player1.ships.get(i).height);
        // bg_sprite.draw(batch);

        batch.end();
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
        if (beingDragged != null && timeDragged > .15f
                && x > Globals.GridLocation1.x
                && x < Globals.GridLocation1.x + Globals.GridSize1.x
                && y > Globals.GridLocation1.y
                && y < Globals.GridLocation1.y + Globals.GridSize1.y) {
            beingDragged.locationSet = true;
        } else if (beingDragged != null)
            beingDragged.resetLocation();
        else {
            for (int i = 0; i < Player1.ships.size(); i++)
                if (touchedShip(Player1.ships.get(i), x, y)) {
                    Player1.ships.get(i).resetLocation();
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
            for (int i = 0; i < Player1.ships.size(); i++)
                if (touchedShip(Player1.ships.get(i), x, y)) {
                    beingDragged = Player1.ships.get(i);
                    Player1.ships.get(i).x = x - Player1.ships.get(i).width / 2;
                    Player1.ships.get(i).y = y - Player1.ships.get(i).height
                            / 2;

                    return true;
                }
        } else {
            timeDragged += Gdx.graphics.getDeltaTime();
            beingDragged.x = x - beingDragged.width / 2;
            beingDragged.y = y - beingDragged.height / 2;
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
