package com.me.battleship;

public class Board {

	public enum SquareType {
		EMPTY, FRIGATE, CRUISER, SUBMARINE, BATTLESHIP, CARRIER, DESTROYED, TARGETED
	}
	
	public enum Orientation {
		VERTICAL, HORIZONTAL
	}
	
	SquareType[][] myGrid;
	SquareType[][] enemyGrid;
	int turns;
	boolean active;
	String name;
	

	Board(String n) {
		myGrid = new SquareType[10][10];
		turns = 0;
		active = false;
		name = n;
		emptyBoard();
	}

	void emptyBoard() {
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				myGrid[i][j] = SquareType.EMPTY;
	}

	void placeShip(int x, int y, SquareType shipClass, Orientation ori) {
		int length;
		
		switch (shipClass){
		case FRIGATE:
			length=1;
			break;
		case CRUISER:
			length=2;
			break;
		case SUBMARINE:
			length=3;
			break;
		case BATTLESHIP:
			length=4;
			break;
		case CARRIER:
			length=5;
			break;
		default:
			length=0;
			break;
		}
		
		if (ori==Orientation.VERTICAL){
			for (int i=0; i<length; i++)
				myGrid[x+i][y]=shipClass;
		}
		else {
			for (int i=0; i<length; i++)
				myGrid[x][y+i]=shipClass;
		}
	}
}
