package com.cit.r00117945.simongame.model;
public class Player {

    /**
     * <p>Player class models a player of the game.</p>
     * <p>Date of last modification 04/11/2015.</p>
     *
     * @author Csaba Farkas csaba.farkas@mycit.ie
     *
     */

    //Private static variables store possible points
    private static final int POINT_FOR_BUTTON = 1;

    //Instance variables
    private String playerName;
    private int currentScore;
    private int highScore;

    /**
     * <p>Constructor method which returns an instance of this class. Player's name is passed in as
     * the only parameter. currentScore and highScore are initialized at 0.</p>
     *
     * @param playerName defines player's name.
     */
    public Player(String playerName) {
        this.playerName = playerName;
        this.currentScore = 0;
        this.highScore = 0;
    }

    /**
     * <p>Accessor method returns player's name.</p>
     *
     * @return player's name.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * <p>Mutator method sets player's name to the name passed to the method.</p>
     *
     * @param playerName player's new name.
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * <p>Accessor method returns player's current score.</p>
     *
     * @return player's current score.
     */
    public int getCurrentScore() {
        return currentScore;
    }

    /**
     * <p>Mutator method sets current score to the value passed to the method.<p>
     *
     * @param currentScore player's new current score.
     */
    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    /**
     * <p>Accessor method returns player's high score.</p>
     *
     * @return player's high score.
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * <p>Mutator method sets high score to the value passed to the method.</p>
     *
     * @param highScore player's high score.
     */
    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    /**
     * <p>Method which increments current score by one when player taps on the
     * correct button.</p>
     */
    public void addPointForButton() {
        this.currentScore += POINT_FOR_BUTTON;
    }

    /**
     * <p>Override {@link Object}.toString() method. Returns a string with player's name.</p>
     *
     * @return player's name.
     */
    @Override
    public String toString() {
        return this.playerName;
    }


}
