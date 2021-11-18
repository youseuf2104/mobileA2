package com.example.mobileassignment2;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;

import androidx.annotation.Nullable;

import java.util.HashMap;

public class locationDatabase extends SQLiteOpenHelper {
    //Name of database
    public static final String DATABASE_NAME = "location.db";

    //Name of table
    public static final String LOCATION_TABLE_NAME = "location";
    //Name of "songs" table columns
    public static final String LOCATION_COLUMN_ID = "id";
    public static final String LOCATION_COLUMN_ADDRESS = "Address";
    public static final String LOCATION_COLUMN_LATITUDE = "Latitude";
    public static final String LOCATION_COLUMN_LONGITUDE = "Longitude";



    private HashMap hp;

    public locationDatabase(Context context) {
        super(context,DATABASE_NAME,null,1);}

    public locationDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        /*
        db.execSQL("create table contacts"+
                        "(id integer primary key AUTOINCREMENT, name text NOT NULL)"
        );  */

        String CREATE_SONGS_TABLE = "CREATE TABLE " + LOCATION_TABLE_NAME  +
                "(" +
                LOCATION_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + // Define a primary key
                LOCATION_COLUMN_ADDRESS+ " TEXT NOT NULL," +//Make name column
                LOCATION_COLUMN_LATITUDE + " REAL NOT NULL," +//Make name column
                LOCATION_COLUMN_LONGITUDE  + " REAL NOT NULL " +//Make name column
                ")";

        db.execSQL(CREATE_SONGS_TABLE ); //Make the table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS LOCATION_TABLE_NAME");
        onCreate(db);
    }

    public void insertLocation (String address, double latitude, double longitude){
        SQLiteDatabase db = this.getWritableDatabase();
        /* Each Content Values object represents a single table row */
        ContentValues contentValues = new ContentValues();
        contentValues.put("Address", address);
        contentValues.put("Latitude", latitude);
        contentValues.put("Longitude", longitude);
        db.insert(LOCATION_TABLE_NAME, null, contentValues);
    }




    public Cursor getAddressData(String Address) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Location where " +
                "Address="+Address+"", null );
        return res;
    }

    public Cursor getLongData(String Address) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select Longitude from Location where " +
                "Address="+Address+"", null );
        return res;
    }

    public int numberOfSongRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, LOCATION_TABLE_NAME);
        return numRows;
    }

    //Not using/modified yet
    public boolean updateContact (Integer id, String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //Update the "name" column value to (parameter) name in the row with the (parameter) id
        contentValues.put("SongTitle", title);
        db.update("songs", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    //WILL MAKE A FUNCTION TO INSERT TO A PLAYLIST

    public Integer deleteLocation (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        //can modify this to delete using other variables such as song name
        return db.delete("location",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }




}
