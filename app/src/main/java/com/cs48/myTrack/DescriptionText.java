package com.cs48.myTrack;

/**
 * A class that stores the descriptionText to be displayed on the DialogFragment
 * @see com.cs48.myTrack.MTListFragment
 */
public class DescriptionText{
    public static String description = "NONE";

    //just in case
    public  DescriptionText(String mDescription){
        description = mDescription;
    }

    //resets descriptionText to "NONE"
    public static void reset(){
        description = "NONE";
    }
}
