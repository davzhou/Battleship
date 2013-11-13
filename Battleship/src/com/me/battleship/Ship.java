package com.me.battleship;

import com.badlogic.gdx.math.Vector2;

public class Ship extends BaseObject {

    public enum ShipClass {
        PATROL(1),
        FRIGATE(2),
        DESTROYER(3),
        BATTLESHIP(4),
        CARRIER(5);

        private int length;

        public int getLength() {
            return length;
        }

        ShipClass(int length) {
            this.length = length;
        }
    }

    public enum Orientation {
        HORIZONTAL(0), VERTICAL(1);

        private Orientation opposite;
        private int ordinal;

        static {
            HORIZONTAL.opposite = VERTICAL;
            VERTICAL.opposite = HORIZONTAL;
        }

        public int getOrdinal() {
            return ordinal;
        }

        Orientation(int i) {
            ordinal = i;
        }

        public Orientation toggle() {
            return opposite;
        }
    }

    private ShipClass shipClass;
    private Orientation orientation, originalOrientation;
    private Vector2 originalCenter, originalDimensions;
    private Vector2[] onSquares;
    private boolean[] activeSquares;
    public boolean locationSet;

    public Ship(int x, int y, ShipClass c, Orientation o, int dimension) {
        super(x, y, dimension * c.getLength(), dimension);
        shipClass = c;
        orientation = o;
        originalOrientation = o;
        originalCenter = center.cpy();
        originalDimensions = dimensions.cpy();
        locationSet = false;
        onSquares = new Vector2[c.getLength()];
        for (int i=0; i< onSquares.length; i++) {
            onSquares[i]=new Vector2();
        }
        activeSquares = new boolean[c.getLength()];
        deselectSquares();
    }

    public void reset() {
        resetShip();
        deselectSquares();
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public ShipClass getShipClass() {
        return shipClass;
    }

    public Vector2[] getOnSquares() {
        return onSquares;
    }

    public boolean[] getActiveSquares() {
        return activeSquares;
    }

    public void deselectSquares(){
        for (int i = 0; i < activeSquares.length; i++)
            activeSquares[i] = false;
    }

    public void changeOrientation(){
        orientation = orientation.toggle();
        float temp = dimensions.x;
        dimensions.x = dimensions.y;
        dimensions.y = temp;
        setTopLeft();
    }

    public void resetShip(){
        orientation = originalOrientation;
        dimensions = originalDimensions;
        move(originalCenter.x, originalCenter.y);
    }



}
