package com.me.battleship;

public abstract class BaseObject {
    int column, row;

    public BaseObject(int x, int y) {
        column = x;
        row = y;
    }
}
