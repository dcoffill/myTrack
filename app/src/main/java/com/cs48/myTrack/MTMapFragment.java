package com.cs48.myTrack;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * A fragment that builds on top of Google's MapFragment to display an interactive map
 */
public class MTMapFragment extends MapFragment {
	private GoogleMap gMap;
	private LocationList list = new LocationList();

	MTMapFragment() {
		super();

	}

	// This is possibly unnecessary, but I'm leaving it because I don't know if we'll need it later
//	@Override
//	public void onViewCreated(View view, Bundle bundle) {
//		super.onViewCreated(view, bundle);
//		//myMap = super.getMap();
//	}

//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//
//	}

	@Override
	public void onStart() {
		super.onStart();
		gMap = super.getMap();

	}

	public void onResume() {
		super.onResume();
		// If gMap is currently null, reassign it
		if (this.gMap == null) {
			gMap = this.getMap();
		}

//		for (MTLocation location: list) {
//			Calendar cal = Calendar.getInstance();
//			cal.setTimeInMillis((long)location.getTime());
//			gMap.addMarker(new MarkerOptions()
//				.position(new LatLng(location.getLatitude(), location.getLongitude()))
//				.title(" Location #" + location.getProvider())
//				.snippet(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US) + " " + cal.get(Calendar.DAY_OF_MONTH)));
//		}
		DatabaseHelper dh = new DatabaseHelper(getActivity());
		List<LocationInfo> liList= dh.getAllLocations();
		int j = 0;
		for (LocationInfo location: liList) {
			gMap.addMarker(new MarkerOptions()
				.position(new LatLng(location.get_Latitude(), location.get_Longitude()))
				.title("Location #" + j)
				.snippet("Lat: " + location.get_Latitude() + "; Long: " + location.get_Longitude()));
			++j;
		}

//		// For now, centers the map above Australia.  Maybe shouldn't be in onResume, since it's
//		// annoying how it re-centers every time you even switch apps...
//		gMap = super.getMap();
//		LatLngBounds australia = new LatLngBounds(new LatLng(-44, 113), new LatLng(-10, 154));
//		gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(australia.getCenter(), 3));

	}
}

