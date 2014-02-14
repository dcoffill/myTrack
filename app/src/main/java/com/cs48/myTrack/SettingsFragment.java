package com.cs48.myTrack;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by david on 1/26/14.
 */

/*
A simple fragment that extends PreferenceFragment.  This serves as a settings interface for the
application
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource and create the resulting fragment
        addPreferencesFromResource(R.xml.preferences);
    }
}
