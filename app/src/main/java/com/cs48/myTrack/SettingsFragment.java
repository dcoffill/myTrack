package com.cs48.myTrack;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by david on 1/26/14.
 */

/* really simple fragment code that creates and Android Preferences Fragment on loading
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}
