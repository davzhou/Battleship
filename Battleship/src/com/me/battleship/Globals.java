package com.me.battleship;

import com.badlogic.gdx.math.Vector2;

public class Globals {
    static final int EMPTY = 0;

    static enum AttackStatus {
        HIT, MISS
    }

    static boolean insideObject(float x, float y, BaseObject o) {
        if (x > o.topLeft.x && x < o.topLeft.x + o.dimensions.x && y > o.topLeft.y
                && y < o.topLeft.y + o.dimensions.y)
            return true;
        return false;
    }

    static boolean insideRegion(float x, float y, Vector2 topleft, Vector2 size) {
        if (x > topleft.x && x < topleft.x + size.x && y > topleft.y
                && y < topleft.y + size.y)
            return true;
        return false;
    }

}
