package com.cit.r00117945.simongame.model;

        import java.util.ArrayList;

public class Stage {

    /**
     * <p>Every game consists of different stages. Every stage of the game has a stage number (level) and
     * a sequence of colors that are displayed.</p>
     * <p>Date of last modification: 04/11/2015.</p>
     *
     * @author Csaba Farkas csaba.farkas@mycit.ie
     */

    //final static variables assigns integer values to different colored buttons
    public static final int RED = 0;
    public static final int BLUE = 1;
    public static final int YELLOw = 2;
    public static final int GREEN = 3;

    private ArrayList<Integer> colorSequence;
    private int numberOfColors;
    private int levelNumber;

    /**
     * <p>Constructor method creates an instance of this class. Level is initialized
     * at 1 and the number of colors on level 1 is 2.</p>
     */
    public Stage() {
        this.colorSequence = new ArrayList<Integer>();
        this.colorSequence.add((int) ((Math.random() * 4)));
        this.colorSequence.add((int) ((Math.random() * 4)));
        this.numberOfColors = 2;
        this.levelNumber = 1;
    }

    /**
     * <p>When player completes a level, game is leveling up. This method increments
     * the level number and it also increments the number of colors in the sequence.</p>
     */
    public void levelUp() {
        this.levelNumber++;
        this.numberOfColors++;
        extendColorSequence();
    }

    /**
     * <p>Method creates random numbers in the range of 0-3 and adds them
     * to the colorSequence. At level 1 two random numbers are generated,
     * and this number is incremented by 1 at every level up. These numbers
     * are representing colors.</p>
     */
    private void extendColorSequence() {

        int colorNumber = (int) ((Math.random() * 4));

        switch(colorNumber) {
            case RED:
                this.colorSequence.add(RED);
                break;
            case BLUE:
                this.colorSequence.add(BLUE);
                break;
            case YELLOw:
                this.colorSequence.add(YELLOw);
                break;
            default:
                this.colorSequence.add(GREEN);
        }

    }

    /**
     * <p>Accessor method which returns colorSequence.</p>
     *
     * @return colorSequence.
     */
    public ArrayList<Integer> getColorSequence() {
        return colorSequence;
    }

    /**
     * <p>Accessor method which returns an integer value indicating the number of
     * buttons flashed at the current stage.</p>
     *
     * @return an integer value.
     */
    public int getNumberOfColors() {
        return numberOfColors;
    }

    /**
     * <p>Mutator method which sets the number of buttons flashed to the value of
     * the parameter.</p>
     *
     * @param numberOfColors is the new number of buttons flashed in a sequence.
     */
    public void setNumberOfColors(int numberOfColors) {
        this.numberOfColors = numberOfColors;
    }

    /**
     * <p>Accessor method which returns the current level number.</p>
     *
     * @return an integer value indicating the current level number.
     */
    public int getLevelNumber() {
        return levelNumber;
    }

    /**
     * <p>Mutator method which sets the level number to the value of the parameter.</p>
     *
     * @param levelNumber is the new level number.
     */
    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }


}

