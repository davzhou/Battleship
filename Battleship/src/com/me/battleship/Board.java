package com.me.battleship;

import java.util.ArrayList;
import java.util.List;

public class Board {

    List<Ship> ships;
    List<Torpedo> torpedoes;
    int[][] myGrid;
    int[][] enemyGrid;
    private int size;
    int turns;
    boolean isActive;
    String name;
    int gridSize;

    Board(String n, int gSize) {
        gridSize = gSize;
        myGrid = new int[gridSize][gridSize];

        size = gridSize;
        turns = 0;
        isActive = false;
        name = n;

        ships = new ArrayList<Ship>();
        torpedoes = new ArrayList<Torpedo>();

        for (int i = 0; i < Globals.numShips; i++) {
            ships.add(new Ship(Globals.shipsRequired[i], Globals.HORIZONTAL));
        }

    }

    void emptyBoard() {
        for (int i = 0; i < gridSize; i++)
            for (int j = 0; j < gridSize; j++)
                myGrid[i][j] = Globals.EMPTY;
        ships.clear();

    }

    void placeShip(int x, int y, int shipClass, int ori) {

        if (ori == Globals.VERTICAL) {
            for (int i = 0; i < shipClass; i++)
                myGrid[x + i][y] = shipClass;
        } else {
            for (int i = 0; i < shipClass; i++)
                myGrid[x][y + i] = shipClass;
        }
        ships.add(new Ship(x, y, shipClass, ori));// gotta change thsi later
    }

    int attackLocation(int x, int y) {

        if (myGrid[x][y] < 0) // any negative square has already been targeted
                              // before
            return 0;
        else if (myGrid[x][y] == 0) { // empty square
            torpedoes.add(new Torpedo(x, y, Globals.AttackStatus.MISS));
            return 1;
        } else { // square has ship
            torpedoes.add(new Torpedo(x, y, Globals.AttackStatus.HIT));
            boolean gameFinished = true;
            for (int i = 0; i < 10 && gameFinished; i++)
                for (int j = 0; j < 10; j++)
                    if (myGrid[i][j] > 0) {
                        gameFinished = false;
                        break;
                    }
            if (gameFinished) // all your ships are dead
                return 3;
            else
                // you still have ships left
                return 2;
        }
    }

    void setEnemy(int[][] e) {
        enemyGrid = e;
    }

    public int getSize() {
        return size;
    }

    void highlightSquares(int x, int y, Ship s) {
        int length = s.shipClass + 1;
        int j = -(length - 1) / 2;
        int temp_x, temp_y;
        if (s.orientation == 0) {
            temp_x = (int) ((x - Globals.TileSize / 2 - Globals.GridTopLeft.x) / Globals.TileSize);
            temp_y = (int) ((y - Globals.GridTopLeft.y) / Globals.TileSize);
            if (temp_y >= 0 && temp_y < Globals.GridDimensions) {
                for (int i = 0; i < s.OnSquares.length; i++) {

                    if (temp_x + j >= 0 && temp_x + j < Globals.GridDimensions) {
                        s.OnSquares[i].x = temp_x + j;
                        s.OnSquares[i].y = temp_y;
                        s.ActiveSquares[i] = true;
                    } else
                        s.ActiveSquares[i] = false;
                    j++;
                }
            } else
                s.unselectSquares();
        } else {
            temp_x = (int) ((x - Globals.GridTopLeft.x) / Globals.TileSize);
            temp_y = (int) ((y - Globals.TileSize / 2 - Globals.GridTopLeft.y) / Globals.TileSize);
            if (temp_x >= 0 && temp_x < Globals.GridDimensions) {
                for (int i = 0; i < s.OnSquares.length; i++) {

                    if (temp_y + j >= 0 && temp_y + j < Globals.GridDimensions) {
                        s.OnSquares[i].x = temp_x;
                        s.OnSquares[i].y = temp_y + j;
                        s.ActiveSquares[i] = true;
                    } else
                        s.ActiveSquares[i] = false;
                    j++;
                }
            } else
                s.unselectSquares();
        }
    }

    void centerShipOnGrid(Ship s) {
        int temp_adjust;
        float temp_adjust_half, x, y;
        temp_adjust = (s.shipClass + 1) / 2;
        float half_tile = .5f * Globals.TileSize;
        temp_adjust_half = (s.shipClass + 1) % 2 * half_tile;
        switch (s.orientation) {
        case Globals.VERTICAL:
            x = Globals.GridTopLeft.x + s.OnSquares[temp_adjust].x
                    * Globals.TileSize + half_tile;
            y = Globals.GridTopLeft.y + s.OnSquares[temp_adjust].y
                    * Globals.TileSize + temp_adjust_half;
            break;
        case Globals.HORIZONTAL:
        default:
            x = Globals.GridTopLeft.x + s.OnSquares[temp_adjust].x
                    * Globals.TileSize + temp_adjust_half;
            y = Globals.GridTopLeft.y + s.OnSquares[temp_adjust].y
                    * Globals.TileSize + half_tile;
            break;

        }
        s.move(x, y);
    }
}
