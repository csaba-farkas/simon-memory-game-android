package com.cit.r00117945.simongame.model;

public class Game {

    /**
     * <p>Every Game has one {@link Player} and a {@link Stage} element.
     * It also has a variable which defines its difficulty, a variable which
     * stores the current high score and a boolean variable which is used to
     * check if player achieved a new high score.</p>
     * <p>Date of last modification: 04/11/2015</p>
     *
     * @author Csaba Farkas csaba.farkas@mycit.ie
     */

    //Instance variables
    private Player player;
    private Stage stage;
    private int difficulty;
    private Integer highScore;
    private boolean isHighScore;

    /**
     * <p>Constructor method creates an instance of this class. A Player object is
     * passed to the constructor and stage is initialized.</p>
     *
     * @param player is a player object modeling the player of the game.
     */
    public Game(Player player, int difficulty) {
        this.player = player;
        this.stage = new Stage();
        this.difficulty = difficulty;
        this.isHighScore = false;
    }

    /**
     * <p>Accessor method returns {@link Player} object of {@link Game}.</p>
     *
     * @return player.
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * <p>Mutator method sets {@link Player} to the object passed to the method.</p>
     *
     * @param player is the new {@link Player}.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * <p>Accessor method returns {@link Stage} of {@link Game}.</p>
     *
     * @return stage
     */
    public Stage getStage() {
        return this.stage;
    }

    /**
     * <p>Mutator method sets {@link Stage} to the object passed to the method.</p>
     *
     * @param stage is defining current stage in the game.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * <p>Accessor method which returns current high score.</p>
     *
     * @return high score.
     */
    public Integer getHighScore() {
        return this.highScore;
    }

    /**
     * <p>Mutator method which sets high score to the value of the parameter.</p>
     *
     * @param highScore new high score.
     */
    public void setHighScore(Integer highScore) {
        this.highScore = highScore;
    }

    /**
     * <p>Accessor method which returns isHighScore field variable.</p>
     *
     * @return isHighScore.
     */
    public boolean getIsHighScore() {
        return this.isHighScore;
    }

    /**
     * <p>Mutator method which sets isHighScore to the value of the parameter.</p>
     *
     * @param isHighScore defines the new value of isHighScore.
     */
    public void setIsHighScore(boolean isHighScore) {
        this.isHighScore = isHighScore;
    }

    /**
     * <p>Accessor method which returns an integer value which defines the difficulty of the game.</p>
     *
     * @return difficulty.
     */
    public int getDifficulty() {
        return this.difficulty;
    }

    /**
     * <p>Mutator method which sets the difficulty of the game to the value of the parameter.</p>
     *
     * @param difficulty new difficulty.
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * <p>Override {@link Object}.toString() method.</p>
     *
     * @return a string with player's name, game difficulty and stage.
     */
    @Override
    public String toString() {
        return "Player: " + this.player + " Stage: " + this.stage + "Difficulty: " + this.difficulty;
    }


}
