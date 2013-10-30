package com.me.battleship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Huyson
 * Date: 10/30/13
 * Time: 4:20 AM
 * To change this template use File | Settings | File Templates.
 * handles all the graphics?
 */
public class Drawer {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ArrayList<Sprite> sprites;
    private Board p1Board;


    public Drawer(Board board){
        p1Board = board;
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight());
        // Set it to an orthographic projection with "y down"
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        camera.setToOrtho(true, screenWidth, screenHeight);
        camera.update();

        // Create a full screen sprite renderer and use the above camera
        batch = new SpriteBatch(screenHeight, screenHeight);
        batch.setProjectionMatrix(camera.combined);
        sprites = new ArrayList<Sprite>();

        //background
        Sprite bg = createSprite("data/board/sea_bg.png", 1024, 768);
        //bgSprite.setSize(1.0f * bgSprite.getWidth(), 1.0f * bgSprite.getHeight());
        //bgSprite.setOrigin(bgSprite.getWidth() / 2, bgSprite.getHeight() / 2);
        //bgSprite.setPosition(-bgSprite.getWidth()/2, -bgSprite.getHeight()/2);
        bg.setPosition(0,0);
        sprites.add(bg);

        //create border
        Sprite p1GridBorder = createSprite("data/board/large_teal_border.png", 512, 512);
        p1GridBorder.setSize(screenHeight * .75f, screenHeight * .75f);
        //bgSprite.setOrigin(bgSprite.getWidth() / 2, bgSprite.getHeight() / 2);
        //bgSprite.setPosition(-bgSprite.getWidth()/2, -bgSprite.getHeight()/2);
        p1GridBorder.setPosition(25, 100);
        sprites.add(p1GridBorder);
        //Sprite p2GridBorder = new Sprite(p1GridBorder);
        //p2GridBorder.setPosition(screenWidth - p1GridBorder.getX() - p2GridBorder.getWidth(), p1GridBorder.getY());
        //sprites.add(p2GridBorder);

        //draw tiles
        float boardSize = board.getSize();
        float tileSize = p1GridBorder.getWidth() / boardSize;
        Sprite tile;
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                tile = createSprite("data/board/black_border.png", 64, 64);
                tile.setSize(tileSize, tileSize);
                tile.setPosition(x*tileSize + p1GridBorder.getX(), y*tileSize + p1GridBorder.getY());
                sprites.add(tile);
                //tile = createSprite("data/board/black_border.png", 64, 64);
                //tile.setSize(tileSize, tileSize);
                //tile.setPosition(x*tileSize + p2GridBorder.getX(), y*tileSize + p2GridBorder.getY());
                //sprites.add(tile);
            }
        }
    }

    private Sprite createSprite(String texturePath, int width, int height){
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        TextureRegion region = new TextureRegion(texture, width, height);
        return new Sprite(region);

    }


    public void draw(){
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        /*
        batch.draw(board, Globals.GridLocation1.x, Globals.GridLocation1.y,
                Globals.GridSize1.x, Globals.GridSize1.y);
        for (int i = 0; i < player1.ships.size(); i++)
            batch.draw(player1.ships.get(i).tex, player1.ships.get(i).x,
                    player1.ships.get(i).y, player1.ships.get(i).width,
                    player1.ships.get(i).height);
                    */
        for (Sprite sprite : sprites) {
            sprite.draw(batch);
        }

        for (int i = 0; i < p1Board.ships.size(); i++) {
            batch.draw(p1Board.ships.get(i).tex, p1Board.ships.get(i).x,
                    p1Board.ships.get(i).y, p1Board.ships.get(i).width,
                    p1Board.ships.get(i).height);
        }

        batch.end();
    }

    public void dispose(){
        batch.dispose();
    }

}
