package com.me.battleship;

import java.util.ArrayList;
import java.util.List;

public class Board {

    List<Ship> ships;
    List<Torpedo> torpedoes;
    int[][] myGrid;
    int[][] enemyGrid;
    int turns;
    boolean active;
    String name;

    Board(String n) {
        myGrid = new int[10][10];
        turns = 0;
        active = false;
        name = n;
        emptyBoard();
        ships = new ArrayList<Ship>();
        torpedoes = new ArrayList<Torpedo>();
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
        ships.add(new Ship(x, y, shipClass, ori));
    }

    int attackLocation(int x, int y) {
        torpedoes.add(new Torpedo(x, y));
        if (myGrid[x][y] < 0) // any negative square has already been targeted
                              // before
            return 0;
        else if (myGrid[x][y] == 0) // empty square
            return 1;
        else { // square has ship
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
}
