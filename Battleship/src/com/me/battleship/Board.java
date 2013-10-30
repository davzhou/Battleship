package com.me.battleship;

import java.util.ArrayList;
import java.util.List;

public class Board {

    public List<Ship> ships;
    public List<Torpedo> torpedoes;
    int[][] myGrid;
    int[][] enemyGrid;
    private int size;
    int turns;
    boolean isActive;
    String name;

    Board(String n, int gridSize) {
        myGrid = new int[gridSize][gridSize];
        size = gridSize;
        turns = 0;
        isActive = false;
        name = n;

        ships = new ArrayList<Ship>();
        torpedoes = new ArrayList<Torpedo>();

        for (int i=0; i<Globals.numShips; i++){
            ships.add(new Ship(Globals.shipsRequired[i], Globals.Orientation.HORIZONTAL));
        }

        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                myGrid[i][j] = Globals.EMPTY;
    }

    void emptyBoard() {
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                myGrid[i][j] = Globals.EMPTY;
        ships.clear();
    }

    void placeShip(int x, int y, int shipClass, Globals.Orientation ori) {

        if (ori == Globals.Orientation.VERTICAL) {
            for (int i = 0; i < shipClass; i++)
                myGrid[x + i][y] = shipClass;
        } else {
            for (int i = 0; i < shipClass; i++)
                myGrid[x][y + i] = shipClass;
        }
        ships.add(new Ship(x, y, shipClass, ori));//gotta change thsi later
    }

    int attackLocation(int x, int y) {

        if (myGrid[x][y] < 0) // any negative square has already been targeted
                              // before
            return 0;
        else if (myGrid[x][y] == 0){ // empty square
            torpedoes.add(new Torpedo(x, y, Globals.AttackStatus.MISS));
            return 1;
        }
        else { // square has ship
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
}
