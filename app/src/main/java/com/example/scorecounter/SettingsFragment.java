package com.example.scorecounter;

import android.os.Bundle;

import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.util.Log;

public class SettingsFragment extends PreferenceFragmentCompat {

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        //getting the preferences
        ListPreference sportPreference = (ListPreference)findPreference("sportPreference");
        ListPreference winnerPreference = (ListPreference)findPreference("winnerImagePreference");
        EditTextPreference contactPreference = (EditTextPreference)findPreference("contactPreference");
        EditTextPreference teamPreference = (EditTextPreference)findPreference("favoriteTeam");

        //Set default summaries
        sportPreference.setSummary("Choose a sport");
        winnerPreference.setSummary("Choose a background for the winner scene");
        contactPreference.setSummary("Enter the phone number of the person you would like to call");
        teamPreference.setSummary("Enter your favorite team");

        //Summary changed when user chooses their sport
        sportPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String sport = "nothing";
                Log.d("VALUE", newValue.toString());
                switch(newValue.toString()){
                    case "res/drawable/volleyballbackground.png":
                        sport = "volleyball";
                        break;

                    case "res/drawable/soccerbackground.jpg":
                        sport = "soccer";
                        break;

                    case "res/drawable/basketballbackground.jpg":
                        sport = "basketball";
                        break;
                }
                sportPreference.setSummary("You've chosen " + sport + " as your sport.");
                return true;
            }
        });

        //Summary changed when the user chooses the winner image
        winnerPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String winnerImage = "nothing";
                Log.d("VALUE", newValue.toString());
                switch(newValue.toString()){
                    case "res/drawable/thumbsup.jpeg":
                        winnerImage = "thumbs up";
                        break;

                    case "res/drawable-v24/trophybackground.png":
                        winnerImage = "trophy";
                        break;

                    case "res/drawable/medal.jpeg":
                        winnerImage = "medal";
                        break;
                }

                winnerPreference.setSummary("You've chosen " + winnerImage + " as your winner background.");
                return true;
            }
        });

        //Summary changed when the user enters a phone number to call for the contact
        contactPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                contactPreference.setSummary("You're contact number is set to " + contactPreference.getText());
                return true;
            }
        });

        //Summary changed when the user chooses the winner image
        teamPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                teamPreference.setSummary("You're favorite team is " + newValue.toString());
                return true;
            }
        });
    }
}