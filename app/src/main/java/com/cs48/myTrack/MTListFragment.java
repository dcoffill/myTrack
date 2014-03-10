package com.cs48.myTrack;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * A simple fragment that displays locations in a list format
 */
public class MTListFragment extends ListFragment{
	private static MTListFragment mList;
	private Context context;

	public static MTListFragment getInstance() {
		if (mList == null) {
			mList = new MTListFragment();
		}
		return mList;
	}

	// Make constructor private, use getInstance() instead
	private MTListFragment() {
		super();
	}

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
		View mView = super.onCreateView(inflater, viewGroup, bundle);
		context = getActivity();
		this.refresh();
		return mView;
	}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) {


                //create a DatabaseHelper to be used
                DatabaseHelper dbHelper = new DatabaseHelper(getActivity());

                //give the time of selected item to search for corresponding location item in the database
                //convert date and time to milliseconds first
                String[] splitString = ((String)(getListView().getItemAtPosition(arg2))).split(":",2);
                //Format: 2014-03-06 14:15:35
                String givenDateString = splitString[1];

                SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss");
                try {
                    Date mDate = sdf.parse(givenDateString);
                    long timeInMilliseconds = mDate.getTime();
                    //search for the item and store its description in ListDialogTransactor.description
                    LocationInfo tmpLocationInfo = dbHelper.getLocationByTime(timeInMilliseconds);
                    String tmpDescription = tmpLocationInfo.get_Description();
                    if (tmpDescription!=null){
                        ListDialogTransactor.description = tmpDescription;
                    } }catch (ParseException e) {
                    e.printStackTrace();
                }
                TextView textView = new TextView(getActivity());
                textView.setSingleLine(false);
                textView.setBackgroundColor(Color.rgb(135,206,250));
                textView.setText("\""+ListDialogTransactor.description+"\"");

                PopupWindow popupWindow = new PopupWindow(textView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                popupWindow.setBackgroundDrawable(new ColorDrawable());

                // Get the View's(the one that was clicked in the Fragment) location
                int location[] = new int[2];
                arg1.getLocationOnScreen(location);

                // Using location, the PopupWindow will be displayed right under anchorView
                popupWindow.showAtLocation(arg1, Gravity.NO_GRAVITY,
                        location[0], location[1] + arg1.getHeight());
                return true;
            }
        });

    }




    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        //super.onListItemClick(l, v, pos, id);
        //Toast.makeText(getActivity(), "Please add location description", Toast.LENGTH_SHORT).show();

        //create a DatabaseHelper to be used
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());

        //give the time of selected item to search for corresponding location item in the database
        //convert date and time to milliseconds first
        String[] splitString = ((String)(getListView().getItemAtPosition(pos))).split(":",2);
        //Format: 2014-03-06 14:15:35
        String givenDateString = splitString[1];

        SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss");
        try {
            Date mDate = sdf.parse(givenDateString);
            long timeInMilliseconds = mDate.getTime();
            //search for the item and store its description in ListDialogTransactor.description
            LocationInfo tmpLocationInfo = dbHelper.getLocationByTime(timeInMilliseconds);
            String tmpDescription = tmpLocationInfo.get_Description();
            if (tmpDescription!=null){
                ListDialogTransactor.description = tmpDescription;
            }
            //store the item get ListDialogTransactor.locationInfo
            ListDialogTransactor.locationInfo = tmpLocationInfo;

            //raw string to get item num
            String givenItemNumPart = splitString[0];
            ListDialogTransactor.itemNum = Integer.parseInt(givenItemNumPart.substring(10,(givenItemNumPart.length())));

        }catch (ParseException e) {
            e.printStackTrace();
        }


            FragmentManager mManager = getFragmentManager();
        MTPopupDialogFragment mPopupDialogFragment = new MTPopupDialogFragment();
        mPopupDialogFragment.show(mManager,"popupDialog");

    }

	public void refresh() {
		if (context == null) {
			return;
		}
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		List<LocationInfo> locationInfoList =  dbHelper.getAllLocations();
		dbHelper.close();
		Collections.reverse(locationInfoList);
		ArrayList<String> myListTitles = new ArrayList<String>();
		int tmpInt = 0;
		for(LocationInfo locInfo:locationInfoList){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(new Date(locInfo.getTime()));
			String tmpStr = "Location #"+(locationInfoList.size()-tmpInt)+": "+dateString;
//                  tmpStr = "Latitude: "+(locInfo.get_Latitude().toString())+"\nLongitude: "+(locInfo.get_Longitude().toString());
			myListTitles.add(tmpStr);
			++tmpInt;
		}

		// Define a new Adapter
		// First parameter - Context
		// Second parameter - Layout for the row
		// Third parameter - ID of the TextView to which the data is written
		// Forth - the Array of data
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, myListTitles);
		// Assign adapter to ListFragment
		this.setListAdapter(adapter);
	}

}


