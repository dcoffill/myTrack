
package com.cs48.myTrack;

import android.app.IntentService;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Criteria;
import android.location.Location;
import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

/**
 * Created by david on 2/23/14
 * This is really bad, since it claims to implement several interfaces and then proceeds to not
 * do so.  But for now it's okay.
 */
public class LocationBackgroundService extends IntentService implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {

	private LocationClient mLocationClient;
	private Location mLocation;
	private final static int
			CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;


	LocationBackgroundService() {
		super("Background Location Service");
	}


	@Override
	public void onCreate() {
		super.onCreate();
		mLocationClient = new LocationClient(this, this, this);

	}

	// Code that requires location should go here.  Ensures no NullPointerExceptions from
	// calling location code when it isn't ready
	public void onConnected(Bundle dataBundle) {
		mLocation = mLocationClient.getLastLocation();
		LocationInfo mLocationInfo = new LocationInfo(mLocation);
		DatabaseHelper db = new DatabaseHelper(this);

		// It appears that SQLite is thread-safe on android, so we shouldn't have to do anything
		// special here with regards to locking
		db.addLocation(mLocationInfo);

		return;
	}

	public void onDisconnected() {
		return;
	}

	public void onConnectionFailed(ConnectionResult connectionResult) {
		return;
	}




	@Override
	protected void onHandleIntent(Intent workIntent) {

		// Hardcoded time, change this later to respect user settings
		//Location mCurrentLocation = mLocationClient.requestLocationUpdates(180*1000l, 10f, Criteria.ACCURACY_FINE,);
		mLocationClient.connect();
		//Location mCurrentLocation = mLocationClient.getLastLocation();
		if (mLocation == null) {
			mLocation = new Location("OMGHAX");
			mLocation.setLatitude(10);
			mLocation.setLongitude(20);
		}

		return;
	}
}
