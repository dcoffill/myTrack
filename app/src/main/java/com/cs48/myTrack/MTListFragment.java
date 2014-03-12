package com.cs48.myTrack;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
        final ListView listView = getListView();
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setBackgroundResource(R.drawable.list_selector);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                  long id, boolean checked) {
                // Here you can do something when items are selected/de-selected,
                // such as update the title in the CAB
                int count = listView.getCheckedItemCount();
                mode.setTitle(count + " items selected");
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                // Respond to clicks on the actions in the CAB
                switch (item.getItemId()) {
                    case R.id.menu_delete:
                        //deleteSelectedItems();
                        int cntChoice = listView.getCount();

                        ArrayList<String> checked = new ArrayList<String>();
                        SparseBooleanArray sparseBooleanArray = listView.getCheckedItemPositions();
                        for (int i = 0; i < cntChoice; i++) {
                            if (sparseBooleanArray.get(i) == true)
                                checked.add(listView.getItemAtPosition(i).toString());
                        }
                        DatabaseHelper dbH = new DatabaseHelper(getActivity());
                        for (String tmpString : checked) {
                            String[] splitString = tmpString.split(":", 2);
                            //Format: 2014-03-06 14:15:35
                            String givenDateString = splitString[1];

                            SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss");
                            try {
                                Date mDate = sdf.parse(givenDateString);
                                long timeInMilliseconds = mDate.getTime();
                                //search for the item and store its description in ListDialogTransactor.description
                                LocationInfo tmpLocationInfo = dbH.getLocationByTime(timeInMilliseconds);
                                dbH.deleteLocation(String.valueOf(tmpLocationInfo.getTime()));
                                getInstance().refresh();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                        mode.finish(); // Action picked, so close the CAB
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Inflate the menu for the CAB
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.context_main, menu);
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // Here you can make any necessary updates to the activity when
                // the CAB is removed. By default, selected items are deselected/unchecked.
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // Here you can perform updates to the CAB due to
                // an invalidate() request
                return false;
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
        dbHelper.close();

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


