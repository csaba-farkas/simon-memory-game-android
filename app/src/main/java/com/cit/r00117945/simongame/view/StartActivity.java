package com.cit.r00117945.simongame.view;

import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;

import com.cit.r00117945.simongame.R;

import java.util.ArrayList;

/**
 * I couldn't figure out a way how to use a gesture in the game so I came up with this
 * idea to have this activity when the app is launched. So player can only launch the app
 * if he/she draws a 'Z' to the screen.
 *
 * Created by Csaba Farkas on 15/12/15.
 */

public class StartActivity extends Activity implements GestureOverlayView.OnGesturePerformedListener {

    private GestureLibrary gestureLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //Add gesture file to be able to recognize gesture
        gestureLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
        if(!gestureLibrary.load()) {
            finish();
        }

        //Create GestureOverlayView and add OnGesturePerformedListener (this class) to it
        GestureOverlayView gestureOverlayView = (GestureOverlayView) findViewById(R.id.gestures);
        gestureOverlayView.addOnGesturePerformedListener(this);
    }

    /**
     * If a 'Z' is drawn to the screen, OptionsActivity is launched.
     * @param gestureOverlayView
     * @param gesture
     */
    @Override
    public void onGesturePerformed(GestureOverlayView gestureOverlayView, Gesture gesture) {
        ArrayList<Prediction> predictions = gestureLibrary.recognize(gesture);
        for(Prediction p : predictions) {
            if(p.name.equals("zGesture") || p.name.equals("ZGesture")) {
                Intent intent = new Intent(StartActivity.this, OptionsActivity.class);
                StartActivity.this.startActivity(intent);
            }
        }
    }
}
