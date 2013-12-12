package com.me.battleship;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class Board extends BaseObject {

    List<Ship> ships;
    List<Torpedo> torpedoes;
    private int[][] fillGrid;
    private int size, tileSize;
    boolean isActive, validShipPlacement;
    String name;
    private Vector2[] onSquares;
    private boolean[] activeSquares;
    private boolean isAI;

    public List<Ship> getShips() {
        return ships;
    }

    public boolean isAI() {
        return isAI;
    }

    public void setAI(boolean AI) {
        isAI = AI;
    }

    public String getName() {
        return name;
    }

    public Board(int x, int y, int u, int v, String n, int size, boolean ai) {
        this(x, y, u, v, n, size);
        setAI(ai);
    }

    public Board(int x, int y, int u, int v, String n, int size) {
        super(x, y, u, v);
        name = n;
        this.size = size;
        fillGrid = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                fillGrid[i][j] = Globals.EMPTY;
            }
        }
        isActive = false;
        torpedoes = new ArrayList<Torpedo>();
        ships = new ArrayList<Ship>();
        tileSize = u / size;

        onSquares = new Vector2[5]; // TODO
        for (int i = 0; i < onSquares.length; i++) {
            onSquares[i] = new Vector2();
        }
        activeSquares = new boolean[5]; // TODO
        deselectSquares();
    }

    public void addShip(Ship s) {
        ships.add(s);
    }

    public void emptyBoard() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                fillGrid[i][j] = Globals.EMPTY;
            }
        ships.clear();
    }

    public boolean attackLocation(int x, int y) {
        int coord[] = pixel2Coord(x, y);
        int gridValue = fillGrid[coord[0]][coord[1]];
        if (gridValue >= 0) {
            if (gridValue > Globals.EMPTY) {
                ships.get(gridValue - 2).hitShip();
            }
            fillGrid[coord[0]][coord[1]] *= -1;
            return true;
        }
        return false;
    }

    public boolean attackLocation() {
        int x = (int) ((getBotX() - getTopX()) * Math.random() + getTopX());
        int y = (int) ((getBotY() - getTopY()) * Math.random() + getTopY());
        return attackLocation(x, y);
    }

    public int getSize() {
        return size;

    }

    public int getTileSize() {
        return tileSize;
    }

    public void centerShipOnSquare(Ship s) {
        int temp_adjust;
        float temp_adjust_half, x, y;
        temp_adjust = (s.getShipClass().getLength()) / 2;
        float half_tile = .5f * tileSize;
        temp_adjust_half = (s.getShipClass().getLength()) % 2 * half_tile;
        switch (s.getOrientation()) {
        case HORIZONTAL:
            x = topLeft.x + getOnSquares()[temp_adjust].x * tileSize + temp_adjust_half;
            y = topLeft.y + getOnSquares()[temp_adjust].y * tileSize + half_tile;
            break;
        case VERTICAL:
        default:
            x = topLeft.x + getOnSquares()[temp_adjust].x * tileSize + half_tile;
            y = topLeft.y + getOnSquares()[temp_adjust].y * tileSize + temp_adjust_half;
            break;

        }
        s.move(x, y);
    }

    public void identifySquares(int x, int y, Ship s) {
        int ship_length = s.getShipClass().getLength();
        int ship_length_offset = -(ship_length - 1) / 2;
        float odd_length_adj = (ship_length + 1) % 2 * tileSize / 2;
        int length, width;
        float min_bound, max_bound;
        validShipPlacement = false;
        switch (s.getOrientation()) {
        case HORIZONTAL:

            length = (int) ((x - odd_length_adj - topLeft.x) / tileSize);
            width = (int) ((y - topLeft.y) / tileSize);
            min_bound = topLeft.y;
            max_bound = topLeft.y + dimensions.y;
            if (y >= min_bound && y < max_bound) {
                validShipPlacement = true;
                for (int i = 0; i < s.getShipClass().getLength(); i++) {

                    if (length + ship_length_offset >= 0 && length + ship_length_offset < getSize()) {

                        getOnSquares()[i].x = length + ship_length_offset;
                        getOnSquares()[i].y = width;
                        getActiveSquares()[i] = true;
                        if (fillGrid[length + ship_length_offset][width] != Globals.EMPTY) {
                            validShipPlacement = false;
                        }

                    } else {
                        getActiveSquares()[i] = false;
                        validShipPlacement = false;
                    }
                    ship_length_offset++;
                }

            } else {
                deselectSquares();
            }
            break;
        case VERTICAL:
        default:
            width = (int) (x - topLeft.x) / tileSize;
            length = (int) (y - odd_length_adj - topLeft.y) / tileSize;
            min_bound = topLeft.x;
            max_bound = topLeft.x + dimensions.x;
            if (x >= min_bound && x < max_bound) {
                validShipPlacement = true;
                for (int i = 0; i < s.getShipClass().getLength(); i++) {
                    if (length + ship_length_offset >= 0 && length + ship_length_offset < getSize()) {

                        getOnSquares()[i].x = width;
                        getOnSquares()[i].y = length + ship_length_offset;
                        getActiveSquares()[i] = true;
                        if (fillGrid[width][length + ship_length_offset] != Globals.EMPTY) {
                            validShipPlacement = false;
                        }

                    } else {
                        getActiveSquares()[i] = false;
                        validShipPlacement = false;
                    }
                    ship_length_offset++;
                }

            } else {
                deselectSquares();
            }
            break;
        }
        for (int i = s.getShipClass().getLength(); i < activeSquares.length; i++)
            activeSquares[i] = false;
    }

    public Vector2[] getOnSquares() {
        return onSquares;
    }

    public boolean[] getActiveSquares() {
        return activeSquares;
    }

    public int[][] getFillGrid() {
        return fillGrid;
    }

    public void selectSquare(int x, int y) {
        int[] coord = pixel2Coord(x, y);
        onSquares[0] = new Vector2((float) coord[0] * tileSize + topLeft.x - tileSize, (float) coord[1] * tileSize
                + topLeft.y - tileSize);
        activeSquares[0] = true;
    }

    public void deselectSquares() {
        for (int i = 0; i < activeSquares.length; i++)
            activeSquares[i] = false;
    }

    public void placeShipOnGrid(Ship s) {
        int shipIndex = 0;
        for (int i = 0; i < ships.size(); i++) {
            if (ships.get(i) == s) {
                shipIndex = i + 2;
                break;
            }
        }

        for (int i = 0; i < activeSquares.length; i++) {
            if (activeSquares[i]) {

                fillGrid[(int) onSquares[i].x][(int) onSquares[i].y] = shipIndex;
                s.getOnSquares()[i] = onSquares[i].cpy();
            }
        }
        s.locationSet = true;
    }

    public void removeShipIfOnGrid(Ship s) {
        if (s.locationSet) {
            for (int i = 0; i < s.getShipClass().getLength(); i++) {
                fillGrid[(int) s.getOnSquares()[i].x][(int) s.getOnSquares()[i].y] = Globals.EMPTY;
            }
            s.locationSet = false;
        }
    }

    public void autoPlace() {
        for (Ship ship : ships) {
            validShipPlacement = false;
            while (!validShipPlacement) {
                removeShipIfOnGrid(ship);
                if (Math.random() > .5) {
                    ship.changeOrientation();
                }
                int x = (int) ((getBotX() - getTopX()) * Math.random() + getTopX());
                int y = (int) ((getBotY() - getTopY()) * Math.random() + getTopY());
                ship.move(x, y);
                identifySquares(x, y, ship);
            }
            centerShipOnSquare(ship);
            placeShipOnGrid(ship);
            deselectSquares();
        }
    }

    public boolean isAllPlaced() {
        for (Ship ship : ships) {
            if (!ship.locationSet) {
                return false;
            }
        }
        return true;
    }

    public boolean isGameOver() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    public int[] pixel2Coord(int x, int y) {
        int[] coord = new int[2];
        coord[0] = (int) (Math.floor((x - topLeft.x) / tileSize));
        coord[1] = (int) (Math.floor((y - topLeft.y) / tileSize));
        return coord;
    }
}
