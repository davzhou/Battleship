package com.me.battleship;

import com.badlogic.gdx.math.Vector2;

public abstract class BaseObject {
    Vector2 Center, TopLeft, Size;

    public BaseObject(float x, float y) {
        Center=new Vector2(x, y);
    }

    public BaseObject(){    }
}
