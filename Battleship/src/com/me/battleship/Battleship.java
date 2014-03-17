package com.me.battleship;

import com.badlogic.gdx.Game;


public class Battleship extends Game {

    private StartScreen startScreen;


    public enum GameState {
        MENU, SETUP, GAME, GAMEOVER;
    }

    @Override
    public void create() {
        startScreen = new StartScreen(this);
        setScreen(startScreen);
    }

}
