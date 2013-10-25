package com.me.battleship;

public class Ship {

    int column, row, shipClass;
    Globals.Orientation orientation;

    Ship(int x, int y, int c, Globals.Orientation o){
        row=x;
        column=y;
        shipClass=c;
        orientation=o;
    }

}
