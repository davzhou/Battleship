package com.me.battleship;

import com.badlogic.gdx.graphics.Texture;

public abstract class BaseObject {
    float x, y, width, height;
    public Texture tex;

    public BaseObject(float x_coord, float y_coord) {
        x = x_coord;
        y = y_coord;
    }

    public BaseObject(){    }
}
