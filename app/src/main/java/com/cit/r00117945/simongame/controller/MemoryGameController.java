package com.cit.r00117945.simongame.controller;

        import android.content.Context;

        import com.cit.r00117945.simongame.interfaces.IDataPersistor;
        import com.cit.r00117945.simongame.interfaces.IMemoryGameGui;
        import com.cit.r00117945.simongame.model.Game;
        import com.cit.r00117945.simongame.model.Player;
        import com.cit.r00117945.simongame.model.Stage;

public class MemoryGameController {

    /**
     * I used this class in my AUI project. However, I removed some of the
     * methods that were not used in my android application.
     * <p>Controller class which connects classes of model package with
     * classes of view package.</p>
     * <p>Date of last modification: 27-11/2015</p>
     *
     * @author Csaba Farkas csaba.farkas@mycit.ie
     */

    //Singleton class
    private static MemoryGameController instance = null;

    //static final variables are used to define the difficulty of the game
    //eg. they define the delay the buttons are flashed
    //The more complicated the game, the faster the buttons flash.
    public static final int EASY_DIFFICULTY = 1000;
    public static final int MEDIUM_DIFFICULTY = 500;

    /**
     * <p>Static function which returns the only one instance of this class.</p>
     * <p>When the program is started, instance = null, so it creates a new
     * instance of this class, before returning.</p>
     *
     * @return the instance of {@link MemoryGameController}
     */
    public static MemoryGameController getInstance() {

        if(instance == null) {
            instance = new MemoryGameController();
        }

        return instance;
    }

    //Field variables
    private Player player;					//Player object of game
    private Game game;						//Game object
    private int difficulty;					//int variable indicating the difficulty of the game
    private Integer highScore;				//Current high score
    private IDataPersistor dataPersistor;	//Instance of data persistor object which implements IDataPersistor


    /**
     * <p>This method creates a new game and sets it to the difficulty level indicated
     * by the integer parameter. Then it assigns the new {@link Game} object to game field
     * variable. It also sets the high score of the new game object to highScore field
     * variable.</p>
     *
     * @param difficulty indicates the difficulty of the new game.
     */
    public void createNewGame(int difficulty) {
        this.difficulty = difficulty;
        this.game = new Game(this.player, this.difficulty);
        this.game.setHighScore(this.highScore);
    }

    /**
     * <p>Mutator method which sets the highScore field variable to the Integer
     * object parameter which is passed to the method.</p>
     * <p>If user runs the program at the first time, there is no data stored in
     * the serialized file, so it sets the value of highScore to 0.</p>
     *
     * @param highScore indicates the current high score.
     */
    public void setHighScore(Integer highScore) {
        if(highScore != null) {
            this.highScore = highScore;
        } else {
            this.highScore = 0;
        }
    }

    /**
     * <p>Accessor method which returns the current high score.</p>
     *
     * @return high score.
     */
    public Integer getHighScore() {
        return this.highScore;
    }

    /**
     * <p>Method which creates a new {@link Player} object. It sets the name
     * of the new player to the value of the String object parameter.</p>
     *
     * @param playerName indicates the name of the new {@link Player}.
     */
    public void createPlayer(String playerName) {
        this.player = new Player(playerName);
    }

    /**
     * <p>Accessor method which returns an integer value indicating the difficulty
     * of the current game.</p>
     *
     * @return an integer value indicating difficulty.
     */
    public int getDifficulty() {
        return this.difficulty;
    }

    /**
     * <p>Mutator method which sets the data persistor of the program to the {@link IDataPersistor}
     * parameter.</p>
     *
     * @param dataPersistor defines a class which implements {@link IDataPersistor} interface.
     */
    public void setDataPersistor(IDataPersistor dataPersistor) {
        this.dataPersistor = dataPersistor;
    }

    /**
     * <p>Accessor method which returns the current color index of the current
     * stage of the game. In the color sequence of the the {@link Stage} class
     * colors are represented as numbers. That's why this method is returning an
     * integer value.</p>
     *
     * @param index defines the index number of the element in the color sequence.
     * @return an integer indicating a color.
     */
    public int getColorIndex(int index) {
        return this.game.getStage().getColorSequence().get(index);
    }

    /**
     * <p>This method is called when player is clicking on the right button during the
     * game. Player is awarded with a point. It also checks whether current score is
     * higher than high score, and changes the value of high score to current score if
     * it is. It also writes high score to the serialized data file so no extra saving
     * is required from the user.</p>
     */
    public void increasePlayerPoint() {
        this.game.getPlayer().addPointForButton();
        if(this.highScore < this.player.getCurrentScore()) {
            this.highScore = this.player.getCurrentScore();
            this.dataPersistor.write(this.highScore);
        }
    }

    /**
     * <p>Accessor method which returns the size of the color sequence of current
     * stage of the game.</p>
     *
     * @return an integer value indicating the size of color sequence list.
     */
    public int getColorSequenceSize() {
        return this.game.getStage().getColorSequence().size();
    }

    /**
     * <p>This function is called when player repeated the color sequence in the right
     * order. It calls the levelUp() function of {@link Stage}.</p>
     */
    public void levelUp() {
        this.game.getStage().levelUp();
    }

    /**
     * <p>Accessor method which returns the current score of the player.</p>
     *
     * @return an integer indicating the current score.
     */
    public int getPlayerScore() {
        return this.player.getCurrentScore();
    }


}

