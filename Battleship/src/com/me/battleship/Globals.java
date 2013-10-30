package com.me.battleship;

import com.badlogic.gdx.math.Vector2;

public class Globals {
    public static final float WHRatio = 1280f/720f;
    public static final int EMPTY = 0;
    public static final int MISSED_TARGET = 1;
    public static final int HIT_TARGET = 2;
    public static final String[] ShipTypes = new String[]{"Frigate", "Cruiser", "Submarine", "Battleship", "Carrier"};
    public static final int[] shipsRequired = new int[]{0, 1, 2, 3, 4};
    public static final int numShips = shipsRequired.length;

    public static final Vector2 GridLocation1 = new Vector2(30f, 0f);
    public static final Vector2 GridSize1 = new Vector2(600f, 700f);
    public static enum Orientation {
        VERTICAL, HORIZONTAL
    }

    public static enum AttackStatus {
        HIT, MISS
    }
}
