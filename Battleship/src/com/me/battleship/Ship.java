package com.me.battleship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Ship extends BaseObject {

    static final Vector2[] ShipLocs = new Vector2[] { new Vector2(600f, 50f),
            new Vector2(600f, 250f), new Vector2(900f, 250f),
            new Vector2(800f, 50f), new Vector2(600f, 450f) };
    static final Vector2[] ShipSizes = new Vector2[] { new Vector2(Globals.TileSize, Globals.TileSize),
            new Vector2(Globals.TileSize*2, Globals.TileSize), new Vector2(Globals.TileSize*3, Globals.TileSize),
            new Vector2(Globals.TileSize*4, Globals.TileSize), new Vector2(Globals.TileSize*5, Globals.TileSize) };

    int shipClass;
    Vector2 OriginalLocation;
    Vector2[] OnSquares;
    boolean[] ActiveSquares;

    boolean locationSet;
    int orientation, originalOrientation;

    Ship(float x, float y, int c, int o) {
        super(x, y);
        OriginalLocation = new Vector2(x, y);
        ShipInit(c, o);
    }

    Ship(int c, int o) {
        super(ShipLocs[c].x, ShipLocs[c].y);
        OriginalLocation = new Vector2(ShipLocs[c].x, ShipLocs[c].y);
        ShipInit(c, o);
    }

    void ShipInit(int c, int o) {
        width = ShipSizes[c].x;
        height = ShipSizes[c].y;
        shipClass = c;
        orientation = o;
        originalOrientation = o;
        locationSet = false;
        OnSquares = new Vector2[c + 1];
        for (int i=0; i<OnSquares.length; i++)
            OnSquares[i]=new Vector2();
        ActiveSquares = new boolean[c + 1];
        unselectSquares();
    }

    void resetLocation() {
        orientation = originalOrientation;
        x = OriginalLocation.x;
        y = OriginalLocation.y;
        unselectSquares();
    }

    void unselectSquares(){
        for (int i = 0; i < ActiveSquares.length; i++)
            ActiveSquares[i] = false;
    }

}
