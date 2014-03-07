package com.cs48.myTrack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Derek on 14-2-16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "dataManager";

    // table name
    private static final String TABLE_INFO = "infoDesk";

    // date index for table info
    private static final String IDX_TABLE_INFO_LOCATION = "index_date";

    // LocationInfos Table Columns names
    private static final String KEY_TIME = "time";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_DESCRIPTION = "description";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    //Please note here is no primary key in the table, time serves as the unique key
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_INFO_TABLE = "CREATE TABLE " + TABLE_INFO + "("
                + KEY_TIME + " TEXT," + KEY_LATITUDE + " TEXT,"
                + KEY_LONGITUDE + " TEXT," + KEY_DESCRIPTION + " TEXT " + ")";
        //String CREATE_INFO_TABLE_INDEX = "CREATE INDEX" + IDX_TABLE_INFO_LOCATION + "ON" + TABLE_INFO + "(" + KEY_TIME + ")";
        db.execSQL(CREATE_INFO_TABLE);
        //db.execSQL(CREATE_INFO_TABLE_INDEX);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INFO);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new location
    public void addLocation(LocationInfo location) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TIME, location.getTime());
        values.put(KEY_LATITUDE, String.valueOf(location.get_Latitude())); // Location latitude
        values.put(KEY_LONGITUDE, String.valueOf(location.get_Longitude())); // Location longitude

        // Inserting Row
        db.insert(TABLE_INFO, null, values);

        db.close(); // Closing database connection
    }

    //Test whether current location is qualified to be added(no same location (i.e.time) exists)
    public  boolean timeCheck(LocationInfo location){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_INFO;
        //read all records in table
        Cursor cursor = db.rawQuery(selectQuery,null);
        //move the cursor to the last record
        cursor.moveToLast();
        if (!cursor.moveToLast()){ //if the database is empty
            return true;
        }
        if(String.valueOf(location.getTime()).equals(cursor.getString(0))){
            return false;
        }else{
            return  true;
        }

    }
/*
    //Not necessary
    // Getting single location
    public LocationInfo getLocation(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_INFO, new String[] { KEY_Time,
                KEY_LATITUDE, KEY_LONGITUDE }, KEY_Time + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        LocationInfo location = new LocationInfo();
        location.setTime(id);
        location.set_Latitude(Double.parseDouble(cursor.getString(1)));
        location.set_Longitude(Double.parseDouble(cursor.getString(2)));

        // return location
        return location;
    }
*/

    // Getting All Locations
    public List<LocationInfo> getAllLocations() {
        List<LocationInfo> locationList = new ArrayList<LocationInfo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_INFO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LocationInfo location = new LocationInfo();
                location.setTime(Long.parseLong(cursor.getString(0)));
                location.set_Latitude(Double.parseDouble(cursor.getString(1)));
                location.set_Longitude(Double.parseDouble(cursor.getString(2)));
                // Adding location to list
                locationList.add(location);
            } while (cursor.moveToNext());
        }

        // return location list
        return locationList;
    }

   // Getting location by date, ex
    public LocationInfo getLocationByTime(long time) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_INFO, new String[] { KEY_TIME,
                KEY_LATITUDE, KEY_LONGITUDE, KEY_DESCRIPTION }, KEY_TIME + "=?",
                new String[] { String.valueOf(time) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        LocationInfo locationInfo = new LocationInfo();
        locationInfo.setTime(time);
        locationInfo.set_Latitude(Double.parseDouble(cursor.getString(1)));
        locationInfo.set_Longitude(Double.parseDouble(cursor.getString(2)));
        locationInfo.set_Description(cursor.getString(3));
        // return locationInfo
        return locationInfo;
}

    // Getting ?? newest locations, warning: less rows in db than required amount may cause problem
    // I'm still working on this method, incomplete state
    //TODO: WELCOME to test and edit, please tell me the results and errors
    public List<LocationInfo> getNewLocations(int amount) {
        List<LocationInfo> locationList = new ArrayList<LocationInfo>();
        // Select Query depend by amount
        String selectQuery = "SELECT  * FROM " + TABLE_INFO + " ASC " + "limit " + String.valueOf(amount);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LocationInfo location = new LocationInfo();
                location.setTime(Long.parseLong(cursor.getString(0)));
                location.set_Latitude(Double.parseDouble(cursor.getString(1)));
                location.set_Longitude(Double.parseDouble(cursor.getString(2)));
                // Adding location to list
                locationList.add(location);
            } while (cursor.moveToNext());
        }

        // return location list
        return locationList;
    }

    // Updating single location
    public int updateLocation(LocationInfo location) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //only description is allowed to edit by user
        values.put(KEY_DESCRIPTION, String.valueOf(location.get_Description()));

        // updating row
        return db.update(TABLE_INFO, values, KEY_TIME + " = ?",
                new String[] { String.valueOf(location.getTime()) });
    }

    // Deleting single location
    public void deleteLocation(String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INFO, KEY_TIME + " = ?",
                new String[] { time });
        db.close();
    }

    // Deleting all location
    public void deleteAllLocations() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INFO, null, null);
        db.close();
    }
    // Getting locations Count
    public int getLocationsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_INFO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
