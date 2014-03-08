
package com.cs48.myTrack;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

/**
 * Created by david on 2/23/14
 * This is really bad, since it claims to implement several interfaces and then proceeds to not
 * do so.  But for now it's okay.
 *
 * This class is a background service that runs in the background, and records the location when
 * it is invoked.
 */
public class LocationBackgroundService extends IntentService implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener,
		LocationListener {

	private LocationClient mLocationClient;
	LocationRequest mLocationRequest;
	private Location mLocation;
	private final static int
			CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	boolean trackingEnabled = false;  // Change this to true if you want the app to do automatic
	// tracking.  May destroy your battery life though, given the current state of the
	// service, so change it back to false if you commit


	public LocationBackgroundService() {
		super("Background Location Service");
	}


	@Override
	public void onCreate() {
		super.onCreate();
		Log.i("@@@@@", "Started LocationBackgroundService");
		mLocationClient = new LocationClient(this, this, this);
		mLocationClient.connect();

		// Set parameters for the LocationClient to use when grabbing locations
		mLocationRequest = LocationRequest.create();
		// Use location request accurate to about a city block
		mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
		// Poll for location updates every 10 minutes (in milliseconds)
		mLocationRequest.setInterval(10 * (60 * 1000));
		// Fastest interval to 2 minutes (in milliseconds)
		mLocationRequest.setFastestInterval(2 * (60 * 1000));
		// Location displacement set to greater than 50 meters, otherwise don't get new location
		mLocationRequest.setSmallestDisplacement(50.0f);

		mLocationClient.disconnect();

	}

	// Code that REQUIRES the LocationClient be ready should go here.  Ensures no
	// NullPointerExceptions from calling location code when it isn't ready
	public void onConnected(Bundle dataBundle) {
		// @@@ NOTICE @@@ THIS WILL NOT WORK UNLESS YOU MANUALLY CHANGE trackingEnabled to true
		// in the instance variables.  This is disabled by default so that simply having this app
		// installed doesn't destroy your battery life

		if (trackingEnabled) {
			if (mLocationClient.isConnected()) {
				//mLocationClient.requestLocationUpdates(mLocationRequest, this);
				LocationInfo mLocationInfo = new LocationInfo(mLocationClient.getLastLocation());
				DatabaseHelper db = new DatabaseHelper(this);

				// It appears that SQLite is thread-safe on android, so we shouldn't have to do anything
				// special here with regards to locking
				db.addLocation(mLocationInfo);
				// Explicitly refresh the MTMapFragment to show the new location marker
				MTMapFragment.getInstance().refresh();
				Log.i("LocationBackgroundService", "Location recorded in background");
			}
			else {
				Log.e("LocationBackgroundService", "Location client was not available to LocationBackgroundService");
			}
		}

		return;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mLocationClient.disconnect();
	}

	public void onDisconnected() {
		return;
	}

	public void onConnectionFailed(ConnectionResult connectionResult) {
		return;
	}




	// May not need anymore
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
		mLocationClient.disconnect();

		return;
	}

	// May not need this anymore
	@Override
	public void onLocationChanged(Location location) {
		// This method runs when the location is changed, as determined by requestLocationUpdates()

		// Write location information to the database here
		LocationInfo mLocationInfo = new LocationInfo(location);
		DatabaseHelper db = new DatabaseHelper(this);
		db.addLocation(mLocationInfo);
		Log.i("-----", "Location added by distance");
	}

}