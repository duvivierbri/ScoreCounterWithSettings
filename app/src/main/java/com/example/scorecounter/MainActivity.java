/*
~*~ UPDATED SCOREKEEPER APP! ~*~

This updated android app is Version 2 of my Volleyball Scorekeeper.

The app is meant to help the user keep track of a the score of their two favorite teams.
They can enter in their team names and keep track of the scores using a button for each side. The first team to reach 5 points wins!

NEW FEATURES!!
Including new art and being able to call and text your friends about what happened after you find out who won!
 */

package com.example.scorecounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "Keeping Track";
    public static final String TEAM1WINS = "com.example.scorecounter.TEAM1WINS";
    public static final String TEAM1NAME = "com.example.scorecounter.TEAM1NAME";
    public static final String TEAM2NAME = "com.example.scorecounter.TEAM2NAME";
    public static final String DIFFERENCE = "com.example.scorecounter.DIFFERENCE";
    public boolean team1Wins = false;
    public boolean teamsEntered = false; //Keeps track of whether or not the
    private int team1Score; //0 is the initial value for the score for Team1
    private int team2Score; //0 is the initial value for the score for Team2
    private int difference;
    private TextView instructions;
    private TextView team1ScoreText;
    private TextView team2ScoreText;
    private TextView team1Title;
    private TextView team2Title;
    private EditText enterTeam1;
    private EditText enterTeam2;
    private Button beginButton;
    private Button team1Button;
    private Button team2Button;


    @Override
    protected void onCreate(Bundle savedInstanceState) { //initial values and functions on startup
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Assigning all variables to views in my design
        team1ScoreText = (TextView)findViewById(R.id.team1Score);
        team2ScoreText = (TextView)findViewById(R.id.team2Score);
        team1Title = (TextView)findViewById(R.id.team1Text);
        team2Title = (TextView)findViewById(R.id.team2Text);
        instructions = (TextView) findViewById(R.id.instructions);
        enterTeam1 = (EditText)findViewById(R.id.enterTeam1);
        enterTeam2 = (EditText)findViewById(R.id.enterTeam2);
        team1Button = (Button)findViewById(R.id.team1Button);
        team2Button = (Button)findViewById(R.id.team2Button);
        beginButton = (Button)findViewById(R.id.beginButton);

        hideCounterViews();

        if (savedInstanceState != null){ //Makes sure scores are preserved in rotation
            team1Score = savedInstanceState.getInt("t1Score");
            team2Score = savedInstanceState.getInt("t2Score");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) { //Values I want saved when rotating
        super.onSaveInstanceState(outState);

        outState.putInt("t1Score", team1Score);
        outState.putInt("t2Score", team2Score);
        outState.putString("t2ScoreText", team2ScoreText.getText().toString());
        outState.putString("t1ScoreText", team1ScoreText.getText().toString());
        outState.putBoolean("entered", teamsEntered);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) { //Values I want restored when finished rotation
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.getInt("t1Score");
        savedInstanceState.getInt("t2Score");
        savedInstanceState.getString("t1ScoreText");
        savedInstanceState.getString("t2ScoreText");
        savedInstanceState.getString("t1Name");
        savedInstanceState.getString("t2Name");
        savedInstanceState.getBoolean("entered");

        if (savedInstanceState.getBoolean("entered") == true){ //Makes sure the TextEdit boxes are hidden if the user has already entered in teams
            showCounterViews();
        }
    }

    public void hideCounterViews(){ //Hides everything that has to do with counting the scores until the user enters team names
        instructions.setVisibility(View.INVISIBLE);
        team1Button.setVisibility(View.INVISIBLE);
        team2Button.setVisibility(View.INVISIBLE);
        team1ScoreText.setVisibility(View.INVISIBLE);
        team2ScoreText.setVisibility(View.INVISIBLE);
        team1Title.setVisibility(View.INVISIBLE);
        team2Title.setVisibility(View.INVISIBLE);
    }

    public void showCounterViews(){ //Shows counting widgets and team names so user can start keeping track of points
        teamsEntered = true;
        instructions.setVisibility(View.VISIBLE);
        team1Button.setVisibility(View.VISIBLE);
        team2Button.setVisibility(View.VISIBLE);
        team1ScoreText.setVisibility(View.VISIBLE);
        team2ScoreText.setVisibility(View.VISIBLE);
        team1Title.setVisibility(View.VISIBLE);
        team2Title.setVisibility(View.VISIBLE);
        enterTeam1.setVisibility(View.INVISIBLE);
        enterTeam1.setEnabled(false);
        enterTeam2.setVisibility(View.INVISIBLE);
        enterTeam2.setEnabled(false);
        beginButton.setVisibility(View.INVISIBLE);
        team1Title.setText(enterTeam1.getText().toString());
        team2Title.setText(enterTeam2.getText().toString());
        team1ScoreText.setText(Integer.toString(team1Score));
        team2ScoreText.setText(Integer.toString(team2Score));
    }

    public void beginButtonCode(View view){ //This is the code for the "Begin" button so they can start counting their points
        Toast noNameMessage = Toast.makeText(this,"BOTH teams need to be filled in!", Toast.LENGTH_LONG); //warning message
        String team1Name = enterTeam1.getText().toString();
        String team2Name = enterTeam2.getText().toString();

        //This if statement block ensures the user can only start counting their points if they entered in a team name for each person
        if (team1Name.matches("")){
            noNameMessage.show();
        } else if (team2Name.matches("")){
            noNameMessage.show();
        } else {
            showCounterViews(); //if they entered both team names, then they can start counting
        }
    }

    //This code is for the team 1 button that adds points to team 1
    public void updateTeam1Score(View view){
        Intent changeToWinner = new Intent(this,WinnerActivity.class); //This will allow me to change activity screens
        team1Score++;
        team1ScoreText.setText(String.valueOf(team1Score));
        difference = team1Score - team2Score;

        if (team1Score == 5) { //This changes to the winner screen if team 1 gets 5 points first
            team1Wins = true;
            //Code only keeps track of whether or not team 1 wins because if team 1 didn't win, that means team 2 wins.
            //It's either or, so there's no reason to keep track of team 2 also.
            team1Score = 5;
            changeToWinner.putExtra(TEAM1WINS, team1Wins); //shares whether or not team 1 is the winner with WinningActivity
            changeToWinner.putExtra(TEAM1NAME, team1Title.getText()); //shares the name of team 1 with WinningActivity
            changeToWinner.putExtra(TEAM2NAME, team2Title.getText()); //shares the name of team 2 with WinningActivity
            changeToWinner.putExtra(DIFFERENCE, difference);
            startActivity(changeToWinner);
        }
    }

    //This code is for the team 2 button which adds one point for team 2
    public void updateTeam2Score(View view){
        Intent changeToWinner = new Intent(this,WinnerActivity.class); //This will allow me to change activity screens
        team2Score = team2Score + 1;
        team2ScoreText.setText(String.valueOf(team2Score));
        difference = team2Score - team1Score;

        if (team2Score == 5) { //This changes to the winner screen if team 2 gets 5 points first
            team2Score = 5;
            changeToWinner.putExtra(TEAM1WINS, team1Wins); //shares whether or not team 1 is the winner with WinningActivity
            changeToWinner.putExtra(TEAM1NAME, team1Title.getText()); //shares the name of team 1 with WinningActivity
            changeToWinner.putExtra(TEAM2NAME, team2Title.getText()); //shares the name of team 2 with WinningActivity
            changeToWinner.putExtra(DIFFERENCE, difference);
            startActivity(changeToWinner); //changes to the WinningActivity
        }
    }
}