package com.me.battleship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Ship extends BaseObject {

    static final Vector2[] ShipLocs = new Vector2[] { new Vector2(600f, 50f),
            new Vector2(600f, 250f), new Vector2(900f, 250f),
            new Vector2(800f, 50f), new Vector2(600f, 450f) };
    static final Vector2[] ShipSizes = new Vector2[] { new Vector2(60f, 80f),
            new Vector2(120f, 80f), new Vector2(180f, 80f),
            new Vector2(240f, 80f), new Vector2(300f, 80f) };

    int shipClass;
    Vector2 OriginalLocation;

    boolean locationSet;
    int orientation, originalOrientation;

    Ship(float x, float y, int c, int o) {
        super(x, y);
        width = ShipSizes[c].x;
        height = ShipSizes[c].y;
        OriginalLocation = new Vector2(x, y);
        shipClass = c;
        orientation = o;
        originalOrientation=o;
        locationSet = false;
        tex = new Texture(Gdx.files.internal("data/board/target.png"));
    }

    Ship(int c, int o) {
        super(ShipLocs[c].x, ShipLocs[c].y);
        width = ShipSizes[c].x;
        height = ShipSizes[c].y;
        OriginalLocation = new Vector2(ShipLocs[c].x, ShipLocs[c].y);
        shipClass = c;
        orientation = o;
        originalOrientation=o;
        locationSet = false;
        tex = new Texture(Gdx.files.internal("data/board/target.png"));
    }

    void resetLocation() {
        orientation = originalOrientation;
        x = OriginalLocation.x;
        y = OriginalLocation.y;
    }

}
