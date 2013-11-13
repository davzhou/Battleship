package com.me.battleship;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.height = 720;
        cfg.title = "Battleship";
        cfg.useGL20 = false;
        cfg.width = 1280;

        new LwjglApplication(new Battleship(), cfg);
    }
}
