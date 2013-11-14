package com.me.battleship;

import java.util.ArrayList;
import java.util.List;

public class Board extends BaseObject {

    List<Ship> ships;
    List<Torpedo> torpedoes;
    private int[][] myGrid, enemyGrid;
    private int size;
    int turns;
    boolean isActive;
    String name;

    public List<Ship> getShips() {
        return ships;
    }

    public Board(int x, int y, int u, int v, String n, int size) {
        super(x, y, u, v);
        name = n;
        this.size = size;
        myGrid = new int[size][size];
        turns = 0;
        isActive = false;
        torpedoes = new ArrayList<Torpedo>();
        ships = new ArrayList<Ship>();
        /*
        for (int i = 0; i < Globals.numShips; i++) {
            ships.add(new Ship(Globals.shipsRequired[i], Globals.HORIZONTAL));
        }
        */
    }

    public void addShip(Ship s) {
        ships.add(s);
    }

    public void emptyBoard() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                myGrid[i][j] = Globals.EMPTY;
        ships.clear();

    }

    /*
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
    */

    public int attackLocation(int x, int y) {

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

    public void setEnemy(int[][] e) {
        enemyGrid = e;
    }

    public int getSize() {
        return size;

    }
}
