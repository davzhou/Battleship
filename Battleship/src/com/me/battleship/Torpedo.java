package com.me.battleship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Torpedo extends BaseObject {

    Globals.AttackStatus status;

    //Texture sizes
    static final float TORPEDO_SIZE_WIDTH = 50f;
    static final float TORPEDO_SIZE_HEIGHT = 40f;

    Torpedo(float x, float y, Globals.AttackStatus s) {
        super(x, y);
        width=TORPEDO_SIZE_WIDTH;
        height=TORPEDO_SIZE_HEIGHT;
        status = s;
    }
}
