package com.me.battleship;

public abstract class BaseObject {
    float x, y, width, height;


    public BaseObject(float x_coord, float y_coord, float w, float h) {
        x = x_coord;
        y = y_coord;
        width = w;
        height = h;
    }
}
