package com.me.battleship;

import com.badlogic.gdx.math.Vector2;

public class Globals {
    static final float WHRatio = 1280f/720f;
    static final int EMPTY = 0;
    static final int MISSED_TARGET = 1;
    static final int HIT_TARGET = 2;
    static final String[] ShipTypes = new String[]{"Frigate", "Cruiser", "Submarine", "Battleship", "Carrier"};
    static final int[] shipsRequired = new int[]{0, 1, 2, 3, 4};
    static final int numShips = shipsRequired.length;

    static final Vector2 GridLocation1 = new Vector2(30f, 0f);
    static final Vector2 GridSize1 = new Vector2(600f, 700f);
    static enum Orientation {
        VERTICAL, HORIZONTAL
    }

    static enum AttackStatus {
        HIT, MISS
    }
}
