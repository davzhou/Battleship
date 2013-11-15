package com.me.battleship;

public class Torpedo extends BaseObject {

    Globals.AttackStatus status;

    // Texture sizes
    static final float TORPEDO_SIZE_WIDTH = 50f;
    static final float TORPEDO_SIZE_HEIGHT = 40f;

    Torpedo(int x, int y, Globals.AttackStatus s) {
        super(x, y);
        status = s;
    }
}
