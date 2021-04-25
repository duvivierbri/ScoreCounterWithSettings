/*
This is VERSION 3.0 code of the Score Counter app
Features include being able to keep track of the scores of your favorite team during a game,call or text
your friend to tell them what happened (or to say hi... :) ) and being able to find volleyball courts near
you so you can either go play it yourself or watch a game!

New features as of this Version is the creation of settings where the user can choose their preferences
of their backgrounds, team names, and the sport.
*/

package com.example.scorecounter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import static android.Manifest.permission.CALL_PHONE;

public class WinnerActivity extends AppCompatActivity {
    private TextView winnerText;
    private String winningTeam;
    private String team1Name;
    private String team2Name;
    private int difference;
    private TextView enterNum;
    private Button share;
    private Button restart;
    private Button findNearYou;
    private FloatingActionButton backButton;
    boolean canEnterNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);

        canEnterNumber = false;
        winnerText = (TextView)findViewById(R.id.winnerScreenText);

        share = (Button)findViewById(R.id.Share);
        restart = (Button)findViewById(R.id.redoButton);
        findNearYou = (Button)findViewById(R.id.nearYou);
        enterNum = (TextView)findViewById(R.id.enterNumberHere);
        enterNum.setVisibility(View.INVISIBLE);
        backButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        backButton.setVisibility(View.INVISIBLE);


        Intent getValue = getIntent(); //Gets the boolean information from MainActivity (Did team 1 win?)
        boolean team1Wins = getValue.getBooleanExtra(MainActivity.TEAM1WINS,false); //Keeps track of team 1 from MainActivity class
        team1Name = getValue.getStringExtra(MainActivity.TEAM1NAME); //Retrieves the team 1 name entered by player
        team2Name = getValue.getStringExtra(MainActivity.TEAM2NAME); //Retrieves the team 2 name entered by player
        difference = getValue.getIntExtra(MainActivity.DIFFERENCE, 0);

        if (team1Wins == true){ //Checks if team 1 wins
            winnerText.setText("CONGRATULATIONS\n" + team1Name + " You won by " + difference + " points!");
            winningTeam = team1Name;
        } else { //If not, that means team 2 wins!
            winnerText.setText("CONGRATULATIONS\n" + team2Name + " You won by " + difference + " points!");
            winningTeam = team2Name;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState2) { //Values I want saved when rotating
        super.onSaveInstanceState(outState2);
        outState2.putString("whoWon", winnerText.getText().toString());
        outState2.putString("currNum", enterNum.getText().toString());
        outState2.putBoolean("canEnter", canEnterNumber);
        outState2.putString("team1", team1Name);
        outState2.putString("team2", team2Name);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState2) { //Values I want restored when finished rotation
        super.onRestoreInstanceState(savedInstanceState2);
        savedInstanceState2.getInt("whoWon");
        savedInstanceState2.getInt("canEnter");
        savedInstanceState2.getString("currNum");
        savedInstanceState2.getString("team1");
        savedInstanceState2.getString("team2");

        if (savedInstanceState2.getBoolean("canEnter") == true){ //Makes sure the textbox to enter the number is still showing
            showEnterNumTextField();
        }
    }

    public void redoButtonCode(View view){ //This code is for the redo button which brings the user back to the MainActivity
        Intent mainActiv = new Intent(this, MainActivity.class);
        startActivity(mainActiv);
    }

    public void sendMessageButton(View view){ //This is the code for the send message button on the winner screen
        Intent smsIntent = new Intent(Intent.ACTION_SEND);
        smsIntent.putExtra(Intent.EXTRA_TEXT, "Hey! Just letting you know that " + winningTeam + " won by " + difference + " points!");
        smsIntent.setType("text/plain");

        Intent showChooserIntent = Intent.createChooser(smsIntent, "ShareWithFriends"); //User can choose what app they would like to use
        try {
            startActivity(showChooserIntent);
        } catch (ActivityNotFoundException e) {
            Log.d("OH NO", "We can't find an activity to text!");
        }
    }

    public void checkPermissionAndCall(View view){ //Makes sure we have permission from the user to use the phone app on their phone
        String friendNum = enterNum.getText().toString();
        Toast noNumberWarning = Toast.makeText(this,"Please fill in a number!", Toast.LENGTH_LONG); //warning message

        if (ContextCompat.checkSelfPermission(WinnerActivity.this, CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
            showEnterNumTextField(); //show the text field so the user can enter a number
        } else {
            requestCallPermission(); //ask if the app can gain access to their call app
        }

        if (canEnterNumber == true){
            if (friendNum.matches("")){ //Checks to make sure they've entered in a number
                noNumberWarning.show(); //Warning to tell the user they haven't entered in a number
            } else {
                callFriend(); // When they've entered a number, make the call!
            }
        }
    }

    public void showEnterNumTextField(){ //Shows the text field where the user can enter a number and the back floating action button
        canEnterNumber = true;
        enterNum.setVisibility(View.VISIBLE);
        backButton.setVisibility(View.VISIBLE);
        share.setVisibility(View.INVISIBLE);
        restart.setVisibility(View.INVISIBLE);
        findNearYou.setVisibility(View.INVISIBLE);
    }

    public void hideEnterNumTextField(){ //Hide the text field where the user can enter a number and the back floating action button
        canEnterNumber = false;
        enterNum.setVisibility(View.INVISIBLE);
        backButton.setVisibility(View.INVISIBLE);
        share.setVisibility(View.VISIBLE);
        restart.setVisibility(View.VISIBLE);
        findNearYou.setVisibility(View.VISIBLE);

    }

    public void callFriend(){ //Function used to initiate the ACTION_CALL intent which allows the user to call whatever number they entered
        Uri phoneNum = Uri.parse("tel:" + String.valueOf(enterNum.getText()));
        Intent callIntent = new Intent(Intent.ACTION_CALL, phoneNum);

        try {
            startActivity(callIntent);
        } catch (ActivityNotFoundException e) {
            Log.d("OH NO", "We cant text!");
        }
    }

    private void requestCallPermission() { //Asks the user if the app can gain access to their phone app with an alert dialog
        new AlertDialog.Builder(this)
                .setTitle("Requesting Permission")
                .setMessage("To call your friend, we need permission to use your phone!")
                .setPositiveButton("Sure!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(WinnerActivity.this, new String[]{CALL_PHONE}, 1); //grand permission
                        showEnterNumTextField(); //bring them to the text box so they can enter their number
                    }
                })
                .setNegativeButton("Nah...", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

    public void openMap(View view){ //Function for the button that finds volleyball courts near you
        // This code is used to search
        Uri searchUri = Uri.parse("geo:0,0?q=volleyball+near+me"); //finds volleyball courts near you
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, searchUri);

        Intent showChooserIntent = Intent.createChooser(mapIntent, "Choose a map app!");
        try {
            startActivity(showChooserIntent);
        }catch (ActivityNotFoundException e){
            Log.d("OH NO", "Activity not found to open a map!!");
        }
    }

    public void goBack(View view){ //Code for the floating action button
        hideEnterNumTextField();
    }
}