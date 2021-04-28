package com.example.scorecounter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

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
        EditTextPreference contactPreference = (EditTextPreference) findPreference("contactPreference");

        //listens for changes in preferences made by user


    }
}