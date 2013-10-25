package com.me.battleship;

public class Ship extends Base_Object {

    int shipClass;
    Globals.Orientation orientation;

    Ship(int x, int y, int c, Globals.Orientation o) {
        super(x, y);
        shipClass = c;
        orientation = o;
    }

}
