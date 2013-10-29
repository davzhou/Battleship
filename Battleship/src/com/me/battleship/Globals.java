package com.me.battleship;

import com.badlogic.gdx.math.Vector2;

public class Globals {

    public static final int EMPTY = 0;
    public static final int FRIGATE = 1;
    public static final int CRUISER = 2;
    public static final int SUBMARINE = 3;
    public static final int BATTLESHIP = 4;
    public static final int CARRIER = 5;
    public static final int TARGETED = -6;
    public static final String[] ShipTypes = new String[]{"Frigate", "Cruiser", "Submarine", "Battleship", "Carrier"};
    public static final int numShips = 5;

    public static enum Orientation {
        VERTICAL, HORIZONTAL
    }

    public static enum AttackStatus {
        HIT, MISS
    }
}
