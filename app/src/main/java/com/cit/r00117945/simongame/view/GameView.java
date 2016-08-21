package com.cit.r00117945.simongame.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.cit.r00117945.simongame.MainActivity;
import com.cit.r00117945.simongame.R;
import com.cit.r00117945.simongame.controller.MemoryGameController;

/**
 * Created by Csaba Farkas on 11/11/15.
 * Custom view class. Includes the game interface with the four buttons.
 * It also includes some game logic.
 */
public class GameView extends View {


    private static final int RED = 0;
    private static final int BLUE = 1;
    private static final int YELLOW = 2;
    private static final int GREEN = 3;

    private Paint paint;
    private Paint borderPaint;
    private Path redButtonPath;
    private Path blueButtonPath;
    private Path yellowButtonPath;
    private Path greenButtonPath;
    private Region redButton;
    private Region blueButton;
    private Region yellowButton;
    private Region greenButton;

    private boolean redTouched;
    private boolean blueTouched;
    private boolean yellowTouched;
    private boolean greenTouched;
    private long delayInMillis;

    private int colorIndex;
    private int highScoreAtStart;

    private boolean isListening;
    private boolean isGameOver;

    private MainActivity myActivity;


    public GameView(Context context) {
        super(context);
        //Initialize instance fields
        this.paint = new Paint();
        this.borderPaint = new Paint();
        this.redButtonPath = new Path();
        this.blueButtonPath = new Path();
        this.yellowButtonPath = new Path();
        this.greenButtonPath = new Path();
        this.redButton = new Region();
        this.blueButton = new Region();
        this.yellowButton = new Region();
        this.greenButton = new Region();
        this.redTouched = false;
        this.blueTouched = false;
        this.yellowTouched = false;
        this.greenTouched = false;
        this.delayInMillis = MemoryGameController.getInstance().getDifficulty();
        this.colorIndex = 0;
        this.isListening = false;
        this.isGameOver = false;
        this.myActivity = (MainActivity) getContext();
        this.highScoreAtStart = MemoryGameController.getInstance().getHighScore();
    }


    /**
     * onDraw method draws all the components of the view. It is a very long
     * method so I used inline comments to explain different parts of it.
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Set paint style to fill paint, stroke paint and stroke width to 7px(?)
        this.paint.setStyle(Paint.Style.FILL);
        this.borderPaint.setStyle(Paint.Style.STROKE);
        this.borderPaint.setStrokeWidth(7);

        //Draw the background with the selected color
        this.paint.setColor(new Color().rgb(44, 42, 42));
        canvas.drawPaint(this.paint);

        //Get center point of screen, outer circle radius and inner circle radius for drawing buttons
        Point centerPoint = new Point(this.getWidth()/2, this.getHeight()/2);
        float outerRadius = this.getWidth()/2 - (float)((this.getWidth()/2)*0.08);
        float innerRadius = this.getWidth()/2 - (float)((this.getWidth()/2)*0.4);

        //Draw a string showing player's current score to the middle and
        //draw "HIGH SCORE: " to the top left corner
        Rect bounds = new Rect();
        this.paint.setColor(Color.RED);
        this.paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        this.paint.setTextSize(70);
        canvas.drawText(this.myActivity.getString(R.string.high_score) + MemoryGameController.getInstance().getHighScore(), 25, 95, this.paint);
        this.paint.setTextSize(120);
        this.paint.getTextBounds(String.valueOf(MemoryGameController.getInstance().getPlayerScore()), 0, String.valueOf(MemoryGameController.getInstance().getPlayerScore()).length(), bounds);
        canvas.drawText(String.valueOf(MemoryGameController.getInstance().getPlayerScore()), getWidth() / 2 - bounds.width() / 2, getHeight() / 2 + bounds.height() / 2, this.paint);

        //Set initial offsets for drawing arcs (buttons)
        int arcOffset = 180;
        int arcSweep = 90;

        //Create two RectF objects, one is the bounds of the outer circle and
        //the other one is the bounds of the inner circle.
        RectF outerRect = new RectF(centerPoint.x - outerRadius, centerPoint.y - outerRadius, centerPoint.x + outerRadius, centerPoint.y + outerRadius);
        RectF innerRect = new RectF(centerPoint.x - innerRadius, centerPoint.y - innerRadius, centerPoint.x + innerRadius, centerPoint.y + innerRadius);

        //Draw red button by drawing a 90 degree arc to the outer circle,
        //another 90 degree arc to the inner circle (opposite way) and
        //close it.
        this.redButtonPath.arcTo(outerRect, arcOffset, arcSweep);
        this.redButtonPath.arcTo(innerRect, arcOffset + arcSweep, -arcSweep);
        this.redButtonPath.close();
        this.paint.setColor(Color.RED);

        //Alpha is set to 50 (button is transparent) if it is not touched or flashed.
        if (!redTouched) {
            this.paint.setAlpha(50);
            canvas.drawPath(this.redButtonPath, this.paint);
        } else {
            canvas.drawPath(this.redButtonPath, this.paint);
            //Similar to Swing Timer, Android uses the postDelayed method. It is a
            //callback function that executes after a specified amount of time. This time
            //is the "delayInMillis" field, which indicates the difficulty of the game.
            //If hard game was selected, this value is 250 millies, in case of medium game
            //it is 500 millis and 1000 in easy game.
            this.postDelayed(new Runnable() {
                @Override
                public void run() {
                    redTouched = false;
                    postInvalidate();
                }
            }, delayInMillis);
        }

        //To capture onTouch events in the buttons, they have to be converted from path to Region.
        //To achieve this, I had to get the bounds of the button and used the computeBounds method of
        //Path class and then the setPath method of the Region class.
        //More info on http://developer.android.com/reference/android/graphics/Region.html
        RectF redButtonBounds = new RectF();
        this.redButtonPath.computeBounds(redButtonBounds, true);
        this.redButton.setPath(this.redButtonPath, new Region((int) redButtonBounds.left, (int) redButtonBounds.top, (int) redButtonBounds.right, (int) redButtonBounds.bottom));

        //Draw border around red button
        this.borderPaint.setColor(new Color().rgb(248, 1, 5));
        canvas.drawPath(this.redButtonPath, this.borderPaint);

        //All the other buttons were created in a similar way as the red button
        //Blue button
        arcOffset += 90;
        this.blueButtonPath.arcTo(outerRect, arcOffset, arcSweep);
        this.blueButtonPath.arcTo(innerRect, arcOffset + arcSweep, -arcSweep);
        this.blueButtonPath.close();
        this.paint.setColor(Color.BLUE);
        if (!blueTouched) {
            this.paint.setAlpha(50);
            canvas.drawPath(this.blueButtonPath, this.paint);
        } else {
            canvas.drawPath(this.blueButtonPath, this.paint);
            this.postDelayed(new Runnable() {
                @Override
                public void run() {
                    blueTouched = false;
                    postInvalidate();
                }
            }, delayInMillis);
        }

        RectF blueButtonBounds = new RectF();
        this.blueButtonPath.computeBounds(blueButtonBounds, true);
        this.blueButton.setPath(this.blueButtonPath, new Region((int) blueButtonBounds.left, (int) blueButtonBounds.top, (int) blueButtonBounds.right, (int) blueButtonBounds.bottom));

        //Draw border around blue button
        this.borderPaint.setColor(new Color().rgb(25, 1, 248));
        canvas.drawPath(this.blueButtonPath, borderPaint);

        //Yellow button
        arcOffset += 90;
        this.yellowButtonPath.arcTo(outerRect, arcOffset, arcSweep);
        this.yellowButtonPath.arcTo(innerRect, arcOffset + arcSweep, -arcSweep);
        this.yellowButtonPath.close();
        this.paint.setColor(Color.YELLOW);
        if (!yellowTouched) {
            this.paint.setAlpha(50);
            canvas.drawPath(this.yellowButtonPath, this.paint);
        } else {
            canvas.drawPath(this.yellowButtonPath, this.paint);
            this.postDelayed(new Runnable() {
                @Override
                public void run() {
                    yellowTouched = false;
                    postInvalidate();
                }
            }, delayInMillis);
        }

        final RectF yellowButtonBounds = new RectF();
        this.yellowButtonPath.computeBounds(yellowButtonBounds, true);
        this.yellowButton.setPath(this.yellowButtonPath, new Region((int) yellowButtonBounds.left, (int) yellowButtonBounds.top, (int) yellowButtonBounds.right, (int) yellowButtonBounds.bottom));

        //Draw border around yellow button
        this.borderPaint.setColor(new Color().rgb(248, 240, 1));
        canvas.drawPath(this.yellowButtonPath, borderPaint);

        //Draw green button
        arcOffset += 90;
        this.greenButtonPath.arcTo(outerRect, arcOffset, arcSweep);
        this.greenButtonPath.arcTo(innerRect, arcOffset + arcSweep, -arcSweep);
        this.greenButtonPath.close();
        this.paint.setColor(Color.GREEN);
        if (!this.greenTouched) {
            this.paint.setAlpha(50);
            canvas.drawPath(this.greenButtonPath, this.paint);
        } else {
            canvas.drawPath(this.greenButtonPath, this.paint);
            this.postDelayed(new Runnable() {
                @Override
                public void run() {
                    greenTouched = false;
                    postInvalidate();
                }
            }, delayInMillis);
        }

        RectF greenButtonBounds = new RectF();
        this.greenButtonPath.computeBounds(greenButtonBounds, true);
        this.greenButton.setPath(this.greenButtonPath, new Region((int) greenButtonBounds.left, (int) greenButtonBounds.top, (int) greenButtonBounds.right, (int) greenButtonBounds.bottom));

        //Draw border around green button
        this.borderPaint.setColor(new Color().rgb(17, 248, 1));
        canvas.drawPath(this.greenButtonPath, this.borderPaint);
    }

    /**
     * To capture touch events, view must implement onTouchEvent method.
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Index of touched button is stored in a local variable.
        int buttonPressed;

        //Touch events are only captured when isListening is true. Eg. during
        //button flash sequence touch events are not captured --> buttons are inactive.
        if(this.isListening) {
            //If a button is touched, the boolean value paired with the button is set to true,
            //a sound is played (sorry for the awful quality, I couldn't achieve any better with
            //Audacity), a check carried out to find out if the right button was touched and if
            //player reached the next level then the canvas is repainted by using postInvalidate.
            //Same sequence of commands for all four buttons.
            if (this.redButton.contains((int) (event.getX()), (int) (event.getY()))) {
                this.redTouched = true;
                myActivity.playSound(generateButtonCode(RED));
                buttonPressed = RED;
                doCheckButtonTouch(buttonPressed, colorIndex);
                colorIndex++;
                doCheckLevelUp();
                postInvalidate();
            } else if (this.blueButton.contains((int) (event.getX()), (int) (event.getY()))) {
                this.blueTouched = true;
                myActivity.playSound(generateButtonCode(BLUE));
                buttonPressed = BLUE;
                doCheckButtonTouch(buttonPressed, colorIndex);
                colorIndex++;
                doCheckLevelUp();
                postInvalidate();
            } else if (this.yellowButton.contains((int) (event.getX()), (int) (event.getY()))) {
                this.yellowTouched = true;
                myActivity.playSound(generateButtonCode(YELLOW));
                buttonPressed = YELLOW;
                doCheckButtonTouch(buttonPressed, colorIndex);
                colorIndex++;
                doCheckLevelUp();
                postInvalidate();
            } else if (this.greenButton.contains((int) (event.getX()), (int) (event.getY()))) {
                this.greenTouched = true;
                myActivity.playSound(generateButtonCode(GREEN));
                buttonPressed = GREEN;
                doCheckButtonTouch(buttonPressed, colorIndex);
                colorIndex++;
                doCheckLevelUp();
                postInvalidate();
            }
        }
        return super.onTouchEvent(event);
    }

    //If player touched all the buttons of the current level in the right
    //sequence, colorIndex variable is set to 0, isListening is set to false
    //and the next level's color sequence is shown to the player.
    private void doCheckLevelUp() {
        if(colorIndex == MemoryGameController.getInstance().getColorSequenceSize()) {
            this.isListening = false;
            this.colorIndex = 0;
            MemoryGameController.getInstance().levelUp();
            this.postDelayed(new Runnable() {
                @Override
                public void run() {
                    flashButtons();
                }
            }, 500);
        }
    }

    private void doCheckButtonTouch(int buttonPressed, int index) {
        //If touched button is the next button in the color sequence, increase player's
        //points and continue
        if(MemoryGameController.getInstance().getColorIndex(index) == buttonPressed) {
            MemoryGameController.getInstance().increasePlayerPoint();
        } else {
            //Game over - player touched the wrong button
            //Stop listening clicks, stop showing color sequence.
            //Show an alert box with a message and an OK button
            //OK button finishes activity and returns to the main menu
            this.isListening = false;
            this.isGameOver = true;

            AlertDialog dialog = new AlertDialog.Builder(myActivity).create();

            //Show different message if new high score is achieved
            if(highScoreAtStart < MemoryGameController.getInstance().getHighScore()) {
                dialog.setMessage(myActivity.getString(R.string.game_over_hs_message) + MemoryGameController.getInstance().getPlayerScore());
            } else {
                dialog.setMessage(myActivity.getString(R.string.game_over_message) + MemoryGameController.getInstance().getPlayerScore());
            }
            dialog.setButton(DialogInterface.BUTTON_NEUTRAL, myActivity.getString(R.string.ok_dialog),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            myActivity.finish();
                        }
                    });
            dialog.show();
        }
    }

    /**
     * This method is responsible for displaying the color sequence of the current level
     * in the right order.
     */
    public void flashButtons() {
        //It works in a very similar way as the buttonTouched method, however, I had to implement
        //a Handler object, to achieve the right timing. The argument of the postDelayed function is
        //a Runnable. If I used only the postDelayed function, the next button would be flashed straight
        //after the previous button started to flash --> no break between flashes and all confusing.
        //The Handler provides an extra delay so the starting of different Threads are synchronised.
        if(!isGameOver) {
            this.isListening = false;
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int color = MemoryGameController.getInstance().getColorIndex(colorIndex);
                    switch (color) {
                        case GameView.RED:
                            redTouched = true;
                            myActivity.playSound(generateButtonCode(RED));
                            postInvalidate();
                            break;
                        case GameView.BLUE:
                            blueTouched = true;
                            myActivity.playSound(generateButtonCode(BLUE));
                            postInvalidate();
                            break;
                        case GameView.YELLOW:
                            yellowTouched = true;
                            myActivity.playSound(generateButtonCode(YELLOW));
                            postInvalidate();
                            break;
                        case GameView.GREEN:
                            greenTouched = true;
                            myActivity.playSound(generateButtonCode(GREEN));
                            postInvalidate();
                    }
                    colorIndex++;
                    if (colorIndex < MemoryGameController.getInstance().getColorSequenceSize()) {
                        handler.postDelayed(this, delayInMillis * 2 + 250);
                    } else {
                        colorIndex = 0;
                        isListening = true;
                    }
                }
            }, delayInMillis);
        }
    }

    /**
     * This method returns an integer which indicates which mp3 file must be
     * played during a button flash or touch. I created 12 different mp3 files so
     * when the game is set to easy, their length is 1000ms, medium 500ms and hard
     * 250ms.
     * @param color
     * @return
     */
    private int generateButtonCode(int color) {
        switch (color) {
            case RED:
                switch (MemoryGameController.getInstance().getDifficulty()) {
                    case MemoryGameController.EASY_DIFFICULTY:
                        return MainActivity.EASY_RED;
                    case MemoryGameController.MEDIUM_DIFFICULTY:
                        return MainActivity.MED_RED;
                    case MemoryGameController.HARD_DIFFICULTY:
                        return MainActivity.HARD_RED;
                }
            case BLUE:
                switch (MemoryGameController.getInstance().getDifficulty()) {
                    case MemoryGameController.EASY_DIFFICULTY:
                        return MainActivity.EASY_BLUE;
                    case MemoryGameController.MEDIUM_DIFFICULTY:
                        return MainActivity.MED_BLUE;
                    case MemoryGameController.HARD_DIFFICULTY:
                        return MainActivity.HARD_BLUE;
                }
            case YELLOW:
                switch (MemoryGameController.getInstance().getDifficulty()) {
                    case MemoryGameController.EASY_DIFFICULTY:
                        return MainActivity.EASY_YELLOW;
                    case MemoryGameController.MEDIUM_DIFFICULTY:
                        return MainActivity.MED_YELLOW;
                    case MemoryGameController.HARD_DIFFICULTY:
                        return MainActivity.HARD_YELLOW;
                }
            case GREEN:
                switch (MemoryGameController.getInstance().getDifficulty()) {
                    case MemoryGameController.EASY_DIFFICULTY:
                        return MainActivity.EASY_GREEN;
                    case MemoryGameController.MEDIUM_DIFFICULTY:
                        return MainActivity.MED_GREEN;
                    case MemoryGameController.HARD_DIFFICULTY:
                        return MainActivity.HARD_GREEN;
                }
        }
        return 0;
    }
}

