package com.me.battleship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Ship extends BaseObject {

    public static final Vector2[] ShipLocs = new Vector2[] {
            new Vector2(600f, 50f), new Vector2(600f, 250f),
            new Vector2(800f, 50f), new Vector2(900f, 250f),
            new Vector2(600f, 500f) };
    public static final Vector2[] ShipSizes = new Vector2[] {
            new Vector2(100f, 100f), new Vector2(200f, 100f),
            new Vector2(300f, 100f), new Vector2(400f, 100f),
            new Vector2(500f, 100f) };

    int shipClass;
    Globals.Orientation orientation;

    Ship(float x, float y, int c, Globals.Orientation o) {
        super(x, y);
        width = ShipSizes[c].x;
        height = ShipSizes[c].y;
        shipClass = c;
        orientation = o;
        tex = new Texture(Gdx.files.internal("data/board/target.png"));
    }

    Ship(int c, Globals.Orientation o) {
        super(ShipLocs[c].x, ShipLocs[c].y);
        width = ShipSizes[c].x;
        height = ShipSizes[c].y;
        shipClass = c;
        orientation = o;
        tex = new Texture(Gdx.files.internal("data/board/target.png"));
    }

}
