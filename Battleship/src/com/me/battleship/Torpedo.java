package com.me.battleship;

import static com.me.battleship.Board.SquareStatus;

public class Torpedo extends BaseObject {

    SquareStatus status;

    //Texture sizes
    static final float TORPEDO_SIZE_WIDTH = 50f;
    static final float TORPEDO_SIZE_HEIGHT = 40f;

    Torpedo(int x,int y, SquareStatus s) {
        super(x, y);
        status = s;
    }
}
