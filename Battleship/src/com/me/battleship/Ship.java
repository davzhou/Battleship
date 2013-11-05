package com.me.battleship;

import com.badlogic.gdx.math.Vector2;

public class Ship extends BaseObject {

    static final Vector2[] ShipLocs = new Vector2[] { new Vector2(630f, 70f),
            new Vector2(700f, 270f), new Vector2(850f, 270f),
            new Vector2(880f, 70f), new Vector2(770f, 470f) };
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
        Size=new Vector2(ShipSizes[c]);
        TopLeft=new Vector2(Center.x-Size.x/2, Center.y-Size.y/2);
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

    void reset() {
        move(OriginalLocation.x, OriginalLocation.y);
        resetOrientation();
        unselectSquares();
    }

    void unselectSquares(){
        for (int i = 0; i < ActiveSquares.length; i++)
            ActiveSquares[i] = false;
    }

    void changeOrientation(){

        orientation=(orientation+1)%2;
        float temp=Size.x;
        Size.x=Size.y;
        Size.y=temp;
        TopLeft.x=Center.x-Size.x/2;
        TopLeft.y=Center.y-Size.y/2;

    }

    void resetOrientation(){
        orientation=originalOrientation;
        Size.x=ShipSizes[shipClass].x;
        Size.y=ShipSizes[shipClass].y;
        TopLeft.x=Center.x-Size.x/2;
        TopLeft.y=Center.y-Size.y/2;
    }

    void move(float x, float y){
        Center.x=x;
        Center.y=y;
        TopLeft.x=Center.x-Size.x/2;
        TopLeft.y=Center.y-Size.y/2;
    }
}
