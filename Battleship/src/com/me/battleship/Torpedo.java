package com.me.battleship;

public class Torpedo extends BaseObject {

    Globals.AttackStatus status;
    Torpedo(int x, int y, Globals.AttackStatus s) {
        super(x, y);
        status=s;
    }
}
