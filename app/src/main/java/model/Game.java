package app.src.main.java.model;

import app.src.main.java.network.Requests;

public class Game {

    private int guessIndex = 0;

    private static final int maxGuesses = 7;

    private boolean gameOver = false;

    private boolean gameWon = false;
    
    public void start() {

    }

    public boolean isOver() {
        return gameOver;
    }

    public String[] getReply(String guess) {
        String[] res =  {"", "", "", "", ""};
        guessIndex++;
        if (guessIndex >= maxGuesses) {
            gameOver = true;
        }
        return res;
    }

    public String getEndState() {
        return gameWon ? Requests.CONST_GAMEWON : Requests.CONST_GAMELOST;
    }

}
