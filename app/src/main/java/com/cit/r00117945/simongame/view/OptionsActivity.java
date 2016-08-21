package com.cit.r00117945.simongame.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.cit.r00117945.simongame.MainActivity;
import com.cit.r00117945.simongame.R;
import com.cit.r00117945.simongame.controller.MemoryGameController;
import com.cit.r00117945.simongame.interfaces.IMemoryGameGui;

/**
 * This activity contains the spinner, seekbar, scrollbar, toggle button and
 * start button.
 * Created by Csaba Farkas on 28/11/15.
 */
public class OptionsActivity extends Activity implements IMemoryGameGui {

    private final int MENU_GOTOWEBSITE = 1, MENU_EXIT = 2, GROUP_DEFAULT = 0;
    private Spinner spinner;
    private int difficulty;
    private ProgressBar progressBar;
    private int progress;
    private TextView textView;
    private SeekBar seekBar;
    private TextView seekBarText;

    //Handler for progressBars progress and secondary progress
    final Handler progressBarHandler = new Handler();
    final Runnable updateProgressBar = new Runnable() {

        @Override
        public void run() {
            if(progressBar != null) {
                progressBar.setProgress(progress);
                if(progress + 10 < 100) {
                    progressBar.setSecondaryProgress(progress + 10);
                }
            }
        }
    };

    /**
     * OnCreate method finds the view field variables in resources, attaches a
     * setOnSeekBarChangeListener to seekbar, and attaches listeners to
     * toggle button and start button.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_options);

        this.spinner = (Spinner) findViewById(R.id.options_spinner);

        this.progressBar = (ProgressBar) findViewById(R.id.startProgressBar);

        this.textView = (TextView) findViewById(R.id.starting_text);

        this.seekBar = (SeekBar) findViewById(R.id.seekBar);
        this.seekBarText = (TextView) findViewById(R.id.seekbar_text);
        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBarText.setVisibility(View.VISIBLE);
                if (seekBar.getProgress() < 33) {
                    seekBarText.setText(R.string.seekbar_easy);
                } else if (seekBar.getProgress() < 66) {
                    seekBarText.setText(R.string.seekbar_medium);
                } else {
                    seekBarText.setText(R.string.seekbar_hard);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        getWindow().getDecorView().setBackgroundColor(new Color().rgb(2, 10, 94));
        setToggleButtonAction();
        setStartButtonAction();
    }

    //An onClickListener is attached to start button. It makes the
    //progress bar and a textview with text "Starting..." visible.
    //It also fires the updateProgressBar method.
    private void setStartButtonAction() {
        Button startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                updateProgressBar();
            }
        });
    }

    //Method to update the progress bar and when progress is 100,
    //it fires the runGame() method to start the game.
    //I used the example for the long computation from the book.
    private void updateProgressBar() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(progress < 100) {
                    computation(1);
                    progress += 10;
                    progressBarHandler.post(updateProgressBar);
                    if(progress >= 100) {
                        runGame();
                    }
                }
            }
        });
        thread.start();
    }

    private void computation(int val) {
        double tmp;
        for (int ii = 0; ii < 1000; ii++)
            for (int jj = 0; jj < 1000; jj++)
                tmp = val * Math.log(ii + 1) / Math.log1p(jj + 1);
    }


    //When toggle button is clicked on, the color theme of the activity is changed from
    //black to blue, or from blue to black.
    private void setToggleButtonAction() {
        final ToggleButton toggleButton = (ToggleButton) this.findViewById(R.id.optionsToggle);
        toggleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (toggleButton.isChecked()) {
                    getWindow().getDecorView().setBackgroundColor(Color.BLACK);
                } else {
                    getWindow().getDecorView().setBackgroundColor(new Color().rgb(2, 10, 94));
                }
            }
        });
    }

    /**
     * Method creating the options menu with two options. "Go to website" and "Exit"
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(GROUP_DEFAULT, MENU_GOTOWEBSITE, 0, getString(R.string.goto_website));
        menu.add(GROUP_DEFAULT, MENU_EXIT, 0, getString(R.string.menu_exit));

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * This method defines what each option in menu should do when selected.
     * "Go to website" launches an Intent.Action_view intent to navigate to
     * the wiki website of Simon Says game.
     * "Exit" exits to home screen.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case MENU_GOTOWEBSITE:
                System.out.println("Going to website...");
                String url = "https://en.wikipedia.org/wiki/Simon_Says";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                return true;
            case MENU_EXIT:
                System.out.println("Exiting...");
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method which starts the game. It creates an intent first. Then checks if the
     * text view below the seekbar is visible. If it is, then user was setting the difficulty
     * of the game using the seekbar. So game difficulty is set either by the value of the
     * selected item of the spinner or by the value of the seekbar.
     * A player and a game is created by the controller and then MainActivity is launched
     */
    @Override
    public void runGame() {
        Intent intent = new Intent(OptionsActivity.this, MainActivity.class);
        if(seekBarText.getVisibility() == View.INVISIBLE) {
            if (this.spinner.getSelectedItemPosition() == 0) {
                this.difficulty = MemoryGameController.EASY_DIFFICULTY;
            } else if (this.spinner.getSelectedItemPosition() == 1) {
                this.difficulty = MemoryGameController.MEDIUM_DIFFICULTY;
            } else {
                this.difficulty = MemoryGameController.HARD_DIFFICULTY;
            }
        } else {
            if (this.seekBarText.equals(R.string.seekbar_easy)) {
                this.difficulty = MemoryGameController.EASY_DIFFICULTY;
            } else if (this.seekBarText.equals(R.string.seekbar_medium)) {
                this.difficulty = MemoryGameController.MEDIUM_DIFFICULTY;
            } else {
                this.difficulty = MemoryGameController.HARD_DIFFICULTY;
            }
        }

        MemoryGameController.getInstance().createPlayer("");
        MemoryGameController.getInstance().createNewGame(this.difficulty);
        OptionsActivity.this.startActivity(intent);
    }

    /**
     * When activity resumes, progress bar is reset, and set to invisible.
     * Also seekbar and "Starting..." text view is set to invisible.
     */
    @Override
    protected void onResume() {
        super.onResume();
        progress = 0;
        textView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        progressBarHandler.post(updateProgressBar);
        seekBar.setProgress(0);
        seekBarText.setVisibility(View.INVISIBLE);
    }

}
