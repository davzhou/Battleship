package com.me.battleship;

public class Globals {
    //Texture sizes
    public static final float TORPEDO_SIZE_WIDTH = 50f;
    public static final float TORPEDO_SIZE_HEIGHT = 40f;

    public static final int EMPTY = 0;
    public static final int FRIGATE = 1;
    public static final int CRUISER = 2;
    public static final int SUBMARINE = 3;
    public static final int BATTLESHIP = 4;
    public static final int CARRIER = 5;
    public static final int TARGETED = -6;

    public static enum Orientation {
        VERTICAL, HORIZONTAL
    }

    public static enum AttackStatus {
        HIT, MISS
    }
}
