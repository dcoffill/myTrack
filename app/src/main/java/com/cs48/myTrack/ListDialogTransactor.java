package com.cs48.myTrack;

/**
 * A class that stores:
 * The descriptionText to be displayed on the DialogFragment
 * The locationInfo to be edited on the DialogFragment
 * The item number that is displayed on the DialogFragment
 * @see com.cs48.myTrack.MTListFragment
 * @see com.cs48.myTrack.MTPopupDialogFragment
 */
public class ListDialogTransactor {
    public static String description = "NONE";
    public static LocationInfo locationInfo = new LocationInfo();
    public static int itemNum = -1;

    //resets descriptionText to "NONE"
    public static void reset(){
        description = "NONE";
        locationInfo = new LocationInfo();
        itemNum = -1;
    }
}
