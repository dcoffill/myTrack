package com.cs48.myTrack;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

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

        //create a polyLine and add each marker as points on the line
        PolylineOptions newLine = new PolylineOptions();

        //create the first marker and draw it in
        LocationInfo locationFirst = liList.get(0);
        gMap.addMarker(new MarkerOptions()
                .position(new LatLng(locationFirst.get_Latitude(), locationFirst.get_Longitude()))
                .title("Location #1 - Start Point")
                .snippet("\""+ locationFirst.get_Description()+"\"")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        newLine.add(new LatLng(locationFirst.get_Latitude(),locationFirst.get_Longitude()));


        //draw all markers left in RED except the last one
		int j = 1;
		for (LocationInfo location: (liList.subList(1,(liList.size()-1)))) {
			gMap.addMarker(new MarkerOptions()
				.position(new LatLng(location.get_Latitude(), location.get_Longitude()))
				.title("Location #" + (j + 1))
				.snippet("\""+ location.get_Description()+"\""))
                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
			newLine.add(new LatLng(location.get_Latitude(), location.get_Longitude()));
			++j;
		}



        //create the last marker and draw it in AZURE
        LocationInfo locationLast = liList.get(liList.size()-1);
        gMap.addMarker(new MarkerOptions()
                .position(new LatLng(locationLast.get_Latitude(), locationLast.get_Longitude()))
                .title("Location #" + liList.size()+" - Latest Point")
                .snippet("\""+ locationLast.get_Description()+"\"")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        newLine.add(new LatLng(locationLast.get_Latitude(),locationLast.get_Longitude()));

        //draw the polyLine on map
        gMap.addPolyline(newLine);


        try{
            LocationInfo tmpLocation = liList.get(liList.size()-1);
            LatLng cameraCtr = new LatLng(tmpLocation.get_Latitude(),tmpLocation.get_Longitude());
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraCtr,17));
        }catch(IndexOutOfBoundsException ex){
//                LocationClient mLocationClient = new LocationClient(this,this,this)
//                Location mCurrentLocation = ((MainActivity)getActivity()).getLocation();
//                LocationInfo mLocationInfo = new LocationInfo(mCurrentLocation);
//                dh.addLocation(mLocationInfo);
//                LocationInfo tmpLocation = liList.get(0);
                LatLng cameraCtr = new LatLng(34.41,-119.84);
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraCtr,11));

        }
//		// For now, centers the map above Australia.  Maybe shouldn't be in onResume, since it's
//		// annoying how it re-centers every time you even switch apps...
//		gMap = super.getMap();
//		LatLngBounds australia = new LatLngBounds(new LatLng(-44, 113), new LatLng(-10, 154));
//		gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(australia.getCenter(), 3));

	}
}

