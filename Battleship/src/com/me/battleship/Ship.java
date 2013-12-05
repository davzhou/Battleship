package com.me.battleship;

import com.badlogic.gdx.math.Vector2;

public class Ship extends BaseObject {

    public enum ShipClass {
        PATROL(1), CRUISER(2), SUBMARINE(3), BATTLESHIP(4), CARRIER(5);

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
    private Vector2 originalCenter, originalDimensions, onSquares[];
    private int unhitSquares;

    public boolean locationSet;

    public Ship(int x, int y, ShipClass c, Orientation o, int dimension) {
        super(x, y, dimension * c.getLength(), dimension);
        shipClass = c;
        orientation = o;
        originalOrientation = o;
        originalCenter = center.cpy();
        originalDimensions = dimensions.cpy();
        locationSet = false;
        onSquares = new Vector2[shipClass.getLength()];
        unhitSquares = shipClass.getLength();

    }

    public void reset() {
        resetShip();
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public Vector2 getOrigDimensions(){
        return originalDimensions;
    }

    public ShipClass getShipClass() {
        return shipClass;
    }

    public Vector2[] getOnSquares() {
        return onSquares;
    }

    public void changeOrientation() {
        orientation = orientation.toggle();
        float temp = dimensions.x;
        dimensions.x = dimensions.y;
        dimensions.y = temp;
        setTopLeft();
    }

    public void resetShip() {
        orientation = originalOrientation;
        dimensions.set(originalDimensions);
        move(originalCenter.x, originalCenter.y);
        locationSet = false;
    }

    public boolean isSunk(){
        if (unhitSquares==0){
            return true;
        }
        return false;
    }

    public void hitShip(){
        unhitSquares--;
    }
}
