package com.me.battleship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Globals {
    static final float WHRatio = 1280f / 720f;
    static final int EMPTY = 0;
    static final int MISSED_TARGET = 1;
    static final int HIT_TARGET = 2;
    static final String[] ShipTypes = new String[] { "Frigate", "Cruiser",
            "Submarine", "Battleship", "Carrier" };
    static final int[] shipsRequired = new int[] { 0, 1, 2, 3, 4 };
    static final int numShips = shipsRequired.length;

    static final Vector2 RotateZoneLocation = new Vector2(600f, 550f);
    static final Vector2 RotateZoneSize = new Vector2(150f, 100f);

    static final Vector2 GridLocation = new Vector2(30f, 30f);
    static final float GridSize = 550f;
    static final int GridDimensions = 9;

    static final int HORIZONTAL = 0;
    static final int VERTICAL = 1;

    static enum AttackStatus {
        HIT, MISS
    }

    static Texture Ship_Sprites;
    static TextureRegion Frigate[];
    static TextureRegion Cruiser[];
    static TextureRegion Battleship[];

    static void Loader() {
        Ship_Sprites = new Texture(Gdx.files.internal("data/board/ships.png"));
        Frigate = TextureFlipper(new TextureRegion(Ship_Sprites, 0, 0, 40, 40),
                new TextureRegion(Ship_Sprites, 40, 0, 40, 40));
        Cruiser = TextureFlipper(
                new TextureRegion(Ship_Sprites, 0, 60, 80, 40),
                new TextureRegion(Ship_Sprites, 0, 100, 40, 80));
        Battleship = TextureFlipper(new TextureRegion(Ship_Sprites, 0, 200,
                160, 40), new TextureRegion(Ship_Sprites, 0, 240, 40, 160));
    }

    static TextureRegion[] TextureFlipper(TextureRegion... textures) {
        for (int i = 0; i < textures.length; i++)
            textures[i].flip(false, true);

        return textures;
    }
}
