package com.me.battleship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Ship extends BaseObject {

    static final Vector2[] ShipLocs = new Vector2[] {
            new Vector2(600f, 50f), new Vector2(600f, 250f),
            new Vector2(900f, 250f), new Vector2(800f, 50f),
            new Vector2(600f, 500f) };
    static final Vector2[] ShipSizes = new Vector2[] {
            new Vector2(100f, 100f), new Vector2(200f, 100f),
            new Vector2(300f, 100f), new Vector2(400f, 100f),
            new Vector2(500f, 100f) };

    int shipClass;
    Vector2 OriginalLocation;
    boolean locationSet;
    Globals.Orientation orientation;

    Ship(float x, float y, int c, Globals.Orientation o) {
        super(x, y);
        width = ShipSizes[c].x;
        height = ShipSizes[c].y;
        OriginalLocation= new Vector2(x, y);
        shipClass = c;
        orientation = o;
        locationSet=false;
        tex = new Texture(Gdx.files.internal("data/board/target.png"));
    }

    Ship(int c, Globals.Orientation o) {
        super(ShipLocs[c].x, ShipLocs[c].y);
        width = ShipSizes[c].x;
        height = ShipSizes[c].y;
        OriginalLocation=new Vector2(ShipLocs[c].x, ShipLocs[c].y);
        shipClass = c;
        orientation = o;
        locationSet=false;
        tex = new Texture(Gdx.files.internal("data/board/target.png"));
    }

    void resetLocation(){
        x=OriginalLocation.x;
        y=OriginalLocation.y;
    }

}
