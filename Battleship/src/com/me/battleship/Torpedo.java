package com.me.battleship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Torpedo extends BaseObject {

    Globals.AttackStatus status;
    public Texture tex; 

    Torpedo(float x, float y, float w, float h, Globals.AttackStatus s) {
        super(x, y, w, h);
        status = s;
        tex=new Texture(Gdx.files.internal("data/board/hit.png"));
    }
}
