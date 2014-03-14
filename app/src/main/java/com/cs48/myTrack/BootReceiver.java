package com.cs48.myTrack;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * A simple receiver that listens for the device boot.  Allows us to notify the AlarmManager on boot
 * that the device should start background tracking (in accordance with user preferences of course).
 */
public class BootReceiver extends BroadcastReceiver {
	AlarmReceiver alarm = new AlarmReceiver();

	/**
	 * This method runs when it the device boots (and auto-tracking is enabled in the SharedPreferences).
	 * @param context
	 * @param intent
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
			Log.i("myTrack/BootReceiver", "Received boot, enabling background service");
			alarm.setAlarm(context);
		}
	}
}
