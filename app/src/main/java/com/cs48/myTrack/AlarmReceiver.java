package com.cs48.myTrack;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
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

	public void setAlarm(Context context) {
		alarmMgr =(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmReceiver.class);
		alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

		//Set an alarm to start about two minutes after starting the application
		alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + (2 * 60 * 1000), alarmIntent);

		// Set the System Alarm manager service to to launch the AlarmReceiver about every 15 minutes
		alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, AlarmManager.INTERVAL_FIFTEEN_MINUTES, AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmIntent);
		Log.i("@@@@@", "Alarm has been scheduled");

		// If/when we get a BootListener, we'll start it here so our AlarmManager runs on boot
	}

	public void cancelAlarm(Context context) {
		if (alarmMgr != null) {
			alarmMgr.cancel(alarmIntent);
		}
		// If/when we have a bootListener, we'll stop it here as well

	}

}
