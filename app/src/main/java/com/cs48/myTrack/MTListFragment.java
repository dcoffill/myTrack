package com.cs48.myTrack;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple fragment that displays locations in a list format
 */
public class MTListFragment extends ListFragment{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        //super.onListItemClick(l, v, pos, id);
        //Toast.makeText(getActivity(), "Please add location description", Toast.LENGTH_SHORT).show();


        //give the time of selected item to search for corresponding location item in the database
        //convert date and time to milliseconds first
        String[] splitString = ((String)(getListView().getItemAtPosition(pos))).split(":",2);
        //Format: 2014-03-06 14:15:35
        String givenDateString = splitString[1];
        Log.e("SplitString","The split string is:" + givenDateString);
        SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss");
        try {
            Date mDate = sdf.parse(givenDateString);
            long timeInMilliseconds = mDate.getTime();
            Log.e("timeInMilli","Time in Milli is: "+timeInMilliseconds);
            //search for the item and store its description in DescriptionText.description
            DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
            LocationInfo tmpLocationInfo = dbHelper.getLocationByTime(timeInMilliseconds);
            String tmpDescription = tmpLocationInfo.get_Description();
            if (tmpDescription!=null){
                DescriptionText.description = tmpDescription;
            }

        }catch (ParseException e) {
            e.printStackTrace();
        }




            FragmentManager mManager = getFragmentManager();
        MTPopupDialogFragment mPopupDialogFragment = new MTPopupDialogFragment();
        mPopupDialogFragment.show(mManager,"popupDialog");

    }

}


