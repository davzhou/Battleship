package com.me.battleship;

import com.badlogic.gdx.Game;


public class Battleship extends Game {

    private StartScreen startScreen;


    @Override
    public void create() {
        startScreen = new StartScreen(this);
        setScreen(startScreen);
    }

}
