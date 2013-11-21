package com.me.battleship;

import com.badlogic.gdx.math.Vector2;

public class Globals {
    static final int EMPTY = 0;
    static final int FILLED = 1;

    static boolean isInside(float x, float y, Vector2 topLeft, Vector2 size) {
        return x > topLeft.x && x < topLeft.x + size.x && y > topLeft.y && y < topLeft.y + size.y;
    }

    static boolean isInside(float x, float y, BaseObject o) {
        return x > o.getTopX() && x < o.getBotX() && y > o.getTopY() && y < o.getBotY();
    }

    static boolean isInsideLoose(float x, float y, BaseObject o, float l) {
        return x > o.getTopX() - l && x < o.getBotX() + l && y > o.getTopY() - l && y < o.getBotY() + l;
    }

    static boolean isInside(BaseObject o1, BaseObject o2) {
        return isInside(o1.getTopX(), o1.getTopY(), o2) && isInside(o1.getBotX(), o1.getBotY(), o2);
    }

    static boolean isInsideLoose(BaseObject o1, BaseObject o2, float l) {
        return isInsideLoose(o1.getTopX(), o1.getTopY(), o2, l) && isInsideLoose(o1.getBotX(), o1.getBotY(), o2, l);
    }

    static boolean isTouching(BaseObject o, Vector2 topLeft, Vector2 size) {
        // TODO
        return true;
    }

    static boolean isTouching(BaseObject o1, BaseObject o2) {
        return o1.getTopX() < o2.getBotX() && o1.getBotX() > o2.getTopX() && o1.getTopY() < o2.getBotY()
                && o1.getBotY() > o2.getTopY();
    }

}
