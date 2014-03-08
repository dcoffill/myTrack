package com.cs48.myTrack;

import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

/**
 * A fragment that builds on top of Google's MapFragment to display an interactive map
 */
public class MTMapFragment extends MapFragment {
	private GoogleMap gMap;
	private static MTMapFragment mMap;
	List<LocationInfo> liList;

	/**
	 * Singleton constructor
	 * @return MTMapFragment that was requested
	 */
	public static MTMapFragment getInstance() {
		if (mMap == null) {
			mMap = new MTMapFragment();
		}
		return mMap;
	}

	private MTMapFragment() {
		super();

	}


	@Override
	public void onStart() {
		super.onStart();
		gMap = super.getMap();

	}

	@Override
	public void onResume() {
		super.onResume();
		// If gMap is currently null, reassign it
		if (this.gMap == null) {
			gMap = this.getMap();
		}

		// Refresh the points on the map
		this.refresh();

		// Attempt to move the camera to the most recently recorded location
        try{
            LocationInfo tmpLocation = liList.get(0);
            LatLng cameraCtr = new LatLng(tmpLocation.get_Latitude(),tmpLocation.get_Longitude());
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraCtr,17));
        }catch(IndexOutOfBoundsException ex){
			// If that doesn't work, move the camera to an arbitrarily selected point

//                LocationClient mLocationClient = new LocationClient(this,this,this)
//                Location mCurrentLocation = ((MainActivity)getActivity()).getLocation();
//                LocationInfo mLocationInfo = new LocationInfo(mCurrentLocation);
//                dh.addLocation(mLocationInfo);
//                LocationInfo tmpLocation = liList.get(0);
                LatLng cameraCtr = new LatLng(34.41,-119.84);
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraCtr,10));

        }
	}

	/**
	 * Clear the map of existing features, then draw the Markers and Polyline onto the map
	 */
	public void refresh() {

		// Check if gMap is null
		if (gMap == null) {
			// If it is, attempt to request a GoogleMap
			gMap = this.getMap();
			if (gMap == null) {
				// if that fails, return
				Log.e("@@@@@", "Error, MTMapFragment could not be refreshed");
				return;
			}

		}

		Log.i("@@@@@", "MTMapFragment refreshed");

		// Clear the map
		gMap.clear();

		// Get database locations (gets every recorded location at the moment)
		DatabaseHelper dh = new DatabaseHelper(getActivity());
		liList= dh.getAllLocations();

		// Make new polyline
		PolylineOptions newLine = new PolylineOptions();

		// Draw individual markers on the map, and add them to the Polyline's path
		int j = 0;
		for (LocationInfo location: liList) {
			gMap.addMarker(new MarkerOptions()
					.position(new LatLng(location.get_Latitude(), location.get_Longitude()))
					.title("Location #" + j)
					.snippet("Lat: " + location.get_Latitude() + "; Long: " + location.get_Longitude()));
			newLine.add(new LatLng(location.get_Latitude(), location.get_Longitude()));
			++j;
		}
		// Add polyline to the map
		Polyline polyline = gMap.addPolyline(newLine);
	}
}

