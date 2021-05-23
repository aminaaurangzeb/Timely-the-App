package com.example.timely;

import android.widget.CheckBox;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
/*
public class Country {

    private String name,time;
    private boolean isSelected;

    public Country(String name, String time, boolean isSelected) {
        this.name = name;
        this.time = time;
        this.isSelected = isSelected;
    }

    public Country() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
*/

import java.util.Date;
        import java.util.UUID;
        import java.io.Serializable;
        import android.content.ContentValues;
        import android.database.sqlite.*;
        import android.database.*;
        import java.text.SimpleDateFormat;
        import java.text.ParseException;

public class Country implements Serializable,Persistable{
    private String id,name,time;
    private boolean isSelected;

    public Country(String name, String time, boolean isSelected) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.time = time;
        this.isSelected = isSelected;
    }

    public Country() {
        this.init();
    }

    private void init(){
        this.id = UUID.randomUUID().toString();
        this.isSelected = false;
    }
    public String getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    public String getType(){
        return getClass().getName();
    }

    public void save(SQLiteDatabase dataStore){


       // SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("KEY_NAME", name);
        values.put("KEY_TIME",time);
        values.put("KEY_IS_SELECTED",isSelected);
        // Inserting Row
        dataStore.insertWithOnConflict("TABLE_COUNTRIES",null,values,SQLiteDatabase.CONFLICT_REPLACE);
        //db.insert("TABLE_COUNTRIES", null, values);
        //2nd argument is String containing nullColumnHack
       // db.close(); // Closing database connection

    }

    public void load(Cursor dataStore){
        name = dataStore.getString(dataStore.getColumnIndex("KEY_NAME"));
        time = dataStore.getString(dataStore.getColumnIndex("KEY_TIME"));
        isSelected = dataStore.getInt(dataStore.getColumnIndex("KEY_IS_SELECTED")) == 1 ? true : false;
    }

    public void delete(SQLiteDatabase dataStore){
        String[] args = new String[1];
        args[0] = id;
        dataStore.delete("TABLE_COUNTRIES","Id = ?",args);
    }
}
