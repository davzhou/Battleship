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

    private boolean being_dragged;

    @Override
    public void create() {
        being_dragged = false;
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

        // background = new
        // Texture(Gdx.files.internal("data/board/sea_bg.png"));
        // background.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        // board = new Texture(Gdx.files.internal("data/board/grid_2.png"));
        // board.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        t = new Torpedo(50f, 50f, Globals.TORPEDO_SIZE_WIDTH,
                Globals.TORPEDO_SIZE_HEIGHT, Globals.AttackStatus.HIT);
        //
        // TextureRegion bg_region = new TextureRegion(background, 0, 0, 1024,
        // 768);
        // TextureRegion board_region = new TextureRegion(board, 0, 0, 950,
        // 950);
        //
        // bg_sprite = new Sprite(bg_region);
        // bg_sprite.setSize(1.0f, 1.0f * bg_sprite.getHeight() /
        // bg_sprite.getWidth());
        // bg_sprite.setOrigin(bg_sprite.getWidth()/2, bg_sprite.getHeight()/2);
        // bg_sprite.setPosition(-bg_sprite.getWidth()/2,
        // -bg_sprite.getHeight()/2);
        //
        // board_sprite = new Sprite(board_region);
        // //board_sprite.setSize(.7f, .7f * board_sprite.getHeight() /
        // board_sprite.getWidth());
        // board_sprite.setSize(.6f, .6f);
        // //board_sprite.setOrigin(bg_sprite.getWidth()/2,
        // bg_sprite.getHeight()/2);
        // board_sprite.setOrigin(0,0);
        // board_sprite.setPosition(-board_sprite.getWidth()*3/4,
        // -board_sprite.getHeight()/2);

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
        // bg_sprite.draw(batch);
        // board_sprite.draw(batch);
        batch.draw(t.tex, t.x, t.y, t.width, t.height);
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
        if (being_dragged) {
            being_dragged = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        if (being_dragged||x > t.x && x < t.x + t.width && y > t.y && y < t.y + t.height) {
            being_dragged = true;
            t.x = x - t.width / 2;
            t.y = y - t.height / 2;
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
}
