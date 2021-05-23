package com.example.timely;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "timely";
    private static final String TABLE_COUNTRIES = "countries";
    private static final String KEY_NAME = "name";
    private static final String KEY_TIME = "time";
    private static final String KEY_IS_SELECTED = "isSelected";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_COUNTRY_TABLE = "CREATE TABLE " + TABLE_COUNTRIES + "("
                 + KEY_NAME + " TEXT PRIMARY KEY,"
                + KEY_TIME + " TEXT," +
                KEY_IS_SELECTED + " INTEGER" + ")";

        db.execSQL(CREATE_COUNTRY_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRIES);

        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    void addCountry(Country country) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, country.getName());
        values.put(KEY_TIME,country.getTime());
        values.put(KEY_IS_SELECTED,country.getSelected());
        // Inserting Row

        db.insert(TABLE_COUNTRIES, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single contact
    Country getCountry(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_COUNTRIES, new String[] {
                        KEY_NAME, KEY_TIME,KEY_IS_SELECTED }, KEY_NAME + "=?",
                new String[] { name }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Country contact = new Country(cursor.getString(0),
                cursor.getString(1), cursor.getInt(2) > 0);
        // return contact
        return contact;
    }

    // code to get all contacts in a list view
    public List<Country> getAllCountries() {
        List<Country> countryList = new ArrayList<Country>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_COUNTRIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Country country = new Country();

                country.setName(cursor.getString(0));
                country.setTime(cursor.getString(1));
                country.setSelected(cursor.getInt(2) > 0);
                // Adding contact to list
                countryList.add(country);
            } while (cursor.moveToNext());
        }

        // return contact list
        return countryList;
    }

    // code to update the single contact
    public int updateCountry(Country country) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, country.getName());
        values.put(KEY_TIME, country.getTime());
        values.put(KEY_IS_SELECTED, country.getSelected());
        // updating row
        return db.update(TABLE_COUNTRIES, values, KEY_NAME + " = ?",
                new String[] { String.valueOf(country.getName()) });
    }

    // Deleting single contact
    public void deleteCountry(Country country) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COUNTRIES, KEY_NAME + " = ?",
                new String[] { String.valueOf(country.getName()) });
        db.close();
    }

    public void dropTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_COUNTRIES);
    }

    // Getting contacts Count
    public int getCountriesCount() {

        String countQuery = "SELECT  * FROM " + TABLE_COUNTRIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();

        // return count
        return cursor.getCount();
    }

}