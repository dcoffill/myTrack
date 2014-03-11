package com.cs48.myTrack;

import android.Manifest;
import android.app.AlarmManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

/**
 * A simple fragment that extends PreferenceFragment.  This serves as a settings interface for the
 * application
 */

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource and create the resulting fragment
        addPreferencesFromResource(R.xml.preferences);
    }

	@Override
	public void onResume() {
		super.onResume();
		// Register the onSharedPreferenceChanged listener
		getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		// Unregister the onSharedPreferenceChanged listener
		getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}

	/** A listener that reacts to changes made in the application's settings, which can trigger immediate
	 * changes to the application's behavior as a result
	 *
	 * @param sharedPreferences A SharedPreferences reference
	 * @param key
	 */
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		AlarmReceiver alarm = new AlarmReceiver();
		// If the "Automatic tracking" switch was changed, react
		if (key.equals("pref_trackingEnabled")) {
			// If the switch was toggled on,
			if (sharedPreferences.getBoolean("pref_trackingEnabled", false)) {
				Log.i("myTrack/SettingsFragment", "Automatic tracking enabled");
				alarm.setAlarm(getActivity());
			}
			else {
				Log.i("myTrack/SettingsFragment", "Automatic tracking disabled");
				alarm.cancelAlarm(getActivity());
			}
		}
		// If the refresh interval was changed, react accordingly
		else if (key.equals("pref_refreshInterval")) {
			// If the interval is changed, cancel the alarm and set again it.  That way the alarm will
			// immediately reflect this new interval preference
			Log.i("myTrack/SettingsFragment", "Refresh interval changed");
			alarm.cancelAlarm(getActivity());
			alarm.setAlarm(getActivity());
		}
	}
}
