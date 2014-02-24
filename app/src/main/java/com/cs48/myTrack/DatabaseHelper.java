package com.cs48.myTrack;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

/**
 * Created by Derek on 14-2-16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "dataManager";

    // table name
    private static final String TABLE_INFO = "infoDesk";

    // date index for table info
    private static final String IDX_TABLE_INFO_LOCATION = "index_date";

    // LocationInfos Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_INFO_TABLE = "CREATE TABLE " + TABLE_INFO + "("
                + KEY_ID + " TEXT," + KEY_LATITUDE + " TEXT,"
                + KEY_LONGITUDE + " TEXT" + ")";
        //String CREATE_INFO_TABLE_INDEX = "CREATE INDEX" + IDX_TABLE_INFO_LOCATION + "ON" + TABLE_INFO + "(" + KEY_ID + ")";
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
        //values.put(KEY_ID, location.getID());
        values.put(KEY_LATITUDE, String.valueOf(location.get_Latitude())); // Location latitude
        values.put(KEY_LONGITUDE, String.valueOf(location.get_Longitude())); // Location longitude

        // Inserting Row
        db.insert(TABLE_INFO, null, values);
        db.close(); // Closing database connection
    }

    // Getting single location
    public LocationInfo getLocation(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_INFO, new String[] { KEY_ID,
                KEY_LATITUDE, KEY_LONGITUDE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        LocationInfo location = new LocationInfo();
        location.setID(id);
        location.set_Latitude(Double.parseDouble(cursor.getString(1)));
        location.set_Longitude(Double.parseDouble(cursor.getString(2)));

        // return location
        return location;
    }

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
                //id has not been set yet
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
        values.put(KEY_LATITUDE, String.valueOf(location.get_Latitude()));
        values.put(KEY_LONGITUDE, String.valueOf(location.get_Longitude()));

        // updating row
        return db.update(TABLE_INFO, values, KEY_ID + " = ?",
                new String[] { String.valueOf(location.getID()) });
    }

    // Deleting single location
    public void deleteLocation(LocationInfo location) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INFO, KEY_ID + " = ?",
                new String[] { String.valueOf(location.getID()) });
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
