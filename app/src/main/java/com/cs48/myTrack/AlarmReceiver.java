package com.cs48.myTrack;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * This class is responsible for interfacing with the system alarm to allow running
 * LocationBackgroundService periodically, even when the application is not in the foreground, and
 * even when the device is suspended.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {

	private AlarmManager alarmMgr;
	private PendingIntent alarmIntent;

	@Override
	public void onReceive(Context context, Intent intent) {

		// Initialize background service
		Intent service = new Intent(context, LocationBackgroundService.class);

		// start the service, this keeps device awake while launching
		startWakefulService(context, service);
	}

	/**
	 * Set the background service to automatically track at a specified interval
	 * @param context
	 */
	public void setAlarm(Context context) {
		alarmMgr =(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmReceiver.class);
		alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

		// Set the System Alarm manager service to to launch the AlarmReceiver according to user preference, about 2 minutes after application start
		Log.i("AlarmReceiver", "Alarm has been scheduled");
//        SharedPreferences settings = context.getSharedPreferences(MainActivity.PREFS_NAME,0);
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		String preferredInterval = settings.getString("pref_refreshInterval","30");

        if (preferredInterval.equals("15")){
            // Set the System Alarm manager service to to launch the AlarmReceiver about every 15 minutes
            alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 120000, AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmIntent);
            Log.i("@@@@@", "Alarm has been scheduled with interval 15 minutes");
        }

        if (preferredInterval.equals("30")){
            // Set the System Alarm manager service to to launch the AlarmReceiver about every 15 minutes
            alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 120000, AlarmManager.INTERVAL_HALF_HOUR, alarmIntent);
            Log.i("@@@@@", "Alarm has been scheduled with interval 30 minutes");
        }

        if (preferredInterval.equals("1")){
            // Set the System Alarm manager service to to launch the AlarmReceiver about every 15 minutes
            alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 120000, AlarmManager.INTERVAL_HOUR, alarmIntent);
            Log.i("@@@@@", "Alarm has been scheduled with interval 1 hour");
        }

        if (preferredInterval.equals("Half Day")){
            // Set the System Alarm manager service to to launch the AlarmReceiver about every 15 minutes
            alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 120000, AlarmManager.INTERVAL_HALF_DAY, alarmIntent);
            Log.i("@@@@@", "Alarm has been scheduled with interval half day");
        }

        if (preferredInterval.equals("A Day")){
            // Set the System Alarm manager service to to launch the AlarmReceiver about every 15 minutes
            alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 120000, AlarmManager.INTERVAL_DAY, alarmIntent);
            Log.i("@@@@@", "Alarm has been scheduled with interval a day");
        }
		Log.i("myTrack/AlarmReceiver", "Alarm scheduled with interval of " + preferredInterval);



		// Allow our background service to start itself when the device is rebooted
		ComponentName receiver = new ComponentName(context, BootReceiver.class);
		PackageManager pm = context.getPackageManager();
		pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
	}

	/**
	 * Disable the background tracking service from running at a specified interval
	 * @param context
	 */
	public void cancelAlarm(Context context) {
		if (alarmMgr != null) {
			alarmMgr.cancel(alarmIntent);
		}

		// Disable our background service starting on boot if the user disables automatic tracking
		ComponentName receiver = new ComponentName(context, BootReceiver.class);
		PackageManager pm = context.getPackageManager();
		pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
	}

}
