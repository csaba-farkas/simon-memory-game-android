package com.cit.r00117945.simongame;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.cit.r00117945.simongame.controller.MemoryGameController;
import com.cit.r00117945.simongame.interfaces.IDataPersistor;
import com.cit.r00117945.simongame.view.GameView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * Activity which implements my custom view GameView class.
 * It is responsible for playing the sounds, and for saving
 * and reading high score.
 * Created by Csaba Farkas on 11/11/15.
 */
public class MainActivity extends Activity implements IDataPersistor{

    public final static int EASY_RED = 0, EASY_BLUE = 1, EASY_YELLOW = 2, EASY_GREEN = 3;
    public final static int MED_RED = 4, MED_BLUE = 5, MED_YELLOW = 6, MED_GREEN = 7;
    public final static int HARD_RED = 8, HARD_BLUE = 9, HARD_YELLOW = 10, HARD_GREEN = 11;

    private final static int MIN_MOVE = 250;
    private static final String FILE_LOCATION = "savedGame";

    private MediaPlayer mp;
    private GameView gameView;
    private GestureDetector gestureDetector;

    /**
     * Read high score from savedGame file.
     * Set the view full screen with no title
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Create MediaPlayer object
        this.mp = new MediaPlayer();

        //Set data persistor in controller to this class and read high score from file.
        MemoryGameController.getInstance().setDataPersistor(this);
        Integer highScore = this.read();
        MemoryGameController.getInstance().setHighScore(highScore);

        //Full screen, no title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Set content view to custom view GameView
        this.gameView = new GameView(this);
        this.setContentView(this.gameView);

        //Create gestureDetector to capture left flings. A left fling brings back the user to the
        //OptionsActivity
        this.gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                if(e2.getX() - e1.getX() > MIN_MOVE) {
                    //Get left fling - go back to main menu
                    finish();
                }
                return true;
            }
        });

        this.gameView.flashButtons();
    }

    /**
     * Capture flings with onTouchEvents.
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    //Write high score to savedGame file
    @Override
    public void write(Integer highScore) {
        ObjectOutputStream oos;
        try {
            FileOutputStream fos = openFileOutput(FILE_LOCATION, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(highScore);
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Read high score from savedGame file
    @Override
    public Integer read() {
        ObjectInputStream ois;
        Integer integer = null;
        try{
            FileInputStream fis = openFileInput("savedGame");
            ois = new ObjectInputStream(fis);
            integer = (Integer) ois.readObject();
            return integer;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Play sound when a button is flashed or touched.
     * Used android MediaPlayer http://developer.android.com/reference/android/media/MediaPlayer.html
     *
     * @param buttonCode indicates the button which was touched or flashed
     */
    public void playSound(int buttonCode) {
        if(mp.isPlaying()) {
            mp.stop();
        }
        mp.release();
        switch(buttonCode) {
            case HARD_RED:
                mp = MediaPlayer.create(this, R.raw.e3);
                mp.start();
                break;
            case HARD_BLUE:
                mp = MediaPlayer.create(this, R.raw.f3);
                mp.start();
                break;
            case HARD_YELLOW:
                mp = MediaPlayer.create(this, R.raw.f_sharp3);
                mp.start();
                break;
            case HARD_GREEN:
                mp = MediaPlayer.create(this, R.raw.g3);
                mp.start();
                break;
            case MED_RED:
                mp = MediaPlayer.create(this, R.raw.e3m);
                mp.start();
                break;
            case MED_BLUE:
                mp = MediaPlayer.create(this, R.raw.f3m);
                mp.start();
                break;
            case MED_YELLOW:
                mp = MediaPlayer.create(this, R.raw.f_sharp3m);
                mp.start();
                break;
            case MED_GREEN:
                mp = MediaPlayer.create(this, R.raw.g3m);
                mp.start();
                break;
            case EASY_RED:
                mp = MediaPlayer.create(this, R.raw.e3e);
                mp.start();
                break;
            case EASY_BLUE:
                mp = MediaPlayer.create(this, R.raw.f3e);
                mp.start();
                break;
            case EASY_YELLOW:
                mp = MediaPlayer.create(this, R.raw.f_sharp3e);
                mp.start();
                break;
            case EASY_GREEN:
                mp = MediaPlayer.create(this, R.raw.g3e);
                mp.start();
                break;

        }
    }
}
