package com.me.battleship;

public class Ship extends BaseObject {

    int shipClass;
    Globals.Orientation orientation;

    Ship(float x, float y, float w, float h, int c, Globals.Orientation o) {
        super(x, y, w, h);
        shipClass = c;
        orientation = o;
    }

}
