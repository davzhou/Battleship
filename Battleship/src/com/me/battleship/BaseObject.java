package com.me.battleship;

import com.badlogic.gdx.math.Vector2;

public class BaseObject {
    Vector2 center, topLeft, dimensions;

    protected BaseObject() {
        this(0, 0, 0, 0);
    }

    protected BaseObject(int x, int y) {
        this(x, y, 0, 0);
    }

    protected BaseObject(int x, int y, int u, int v) {
        topLeft = new Vector2(x, y);
        center = new Vector2(0, 0);
        dimensions = new Vector2(u, v);
        setCenter();
    }

    public void move(float x, float y) {
        center.x = x;
        center.y = y;
        setTopLeft();
    }

    public void moveTopLeft(float x, float y) {
        topLeft.x = x;
        topLeft.y = y;
        setCenter();
    }

    protected void setTopLeft() {
        topLeft.x = center.x - dimensions.x / 2;
        topLeft.y = center.y - dimensions.y / 2;
    }

    protected void setCenter() {
        center.x = dimensions.x / 2 + topLeft.x;
        center.y = dimensions.y / 2 + topLeft.y;
    }

    public Vector2 getTopLeft() {
        return topLeft;
    }

    public float getTopX() {
        return topLeft.x;
    }

    public float getTopY() {
        return topLeft.y;
    }

    public Vector2 getBottomRight() {
        return new Vector2(getBotX(), getBotY());
    }

    public float getBotX() {
        return topLeft.x + dimensions.x;
    }

    public float getBotY() {
        return topLeft.y + dimensions.y;
    }
}
