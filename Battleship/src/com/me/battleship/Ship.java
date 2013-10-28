package com.me.battleship;

public class Ship extends BaseObject {

    int shipClass;
    Globals.Orientation orientation;

    Ship(float x, float y, int c, Globals.Orientation o) {
        super(x, y);
        shipClass = c;
        orientation = o;
    }

}
