package com.me.battleship;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Battleship implements ApplicationListener {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture background;
    private Texture board;
    private Sprite bg_sprite;
    private Sprite board_sprite;

    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(1, h/w);
        batch = new SpriteBatch();

        background = new Texture(Gdx.files.internal("data/board/sea_bg.png"));
        background.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        board = new Texture(Gdx.files.internal("data/board/grid_2.png"));
        board.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        TextureRegion bg_region = new TextureRegion(background, 0, 0, 1024, 768);
        TextureRegion board_region = new TextureRegion(board, 0, 0, 950, 950);

        bg_sprite = new Sprite(bg_region);
        bg_sprite.setSize(1.0f, 1.0f * bg_sprite.getHeight() / bg_sprite.getWidth());
        bg_sprite.setOrigin(bg_sprite.getWidth()/2, bg_sprite.getHeight()/2);
        bg_sprite.setPosition(-bg_sprite.getWidth()/2, -bg_sprite.getHeight()/2);

        board_sprite = new Sprite(board_region);
        //board_sprite.setSize(.7f, .7f * board_sprite.getHeight() / board_sprite.getWidth());
        board_sprite.setSize(.6f, .6f);
        //board_sprite.setOrigin(bg_sprite.getWidth()/2, bg_sprite.getHeight()/2);
        board_sprite.setOrigin(0,0);
        board_sprite.setPosition(-board_sprite.getWidth()*3/4, -board_sprite.getHeight()/2);
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
        bg_sprite.draw(batch);
        board_sprite.draw(batch);
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
}
