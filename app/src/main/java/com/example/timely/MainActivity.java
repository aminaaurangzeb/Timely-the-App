package com.example.timely;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Xml;
import android.view.View;

import com.example.timely.ClockAdapter;
import com.example.timely.CountryAdapter;
import com.example.timely.Country;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.TimeZone;
import java.util.stream.Collectors;

import static java.util.logging.Level.parse;

public class MainActivity extends AppCompatActivity {
    //List<Country> countries;

    private ClockAdapter clockAdapter;

/*//////////////
    timezoneservice dataService;
    boolean bound = false;
    private ServiceConnection connection = new ServiceConnection(){
        public void onServiceConnected(ComponentName className, IBinder binder){
            dataService = ((timezoneservice.LocalBinder) binder).getService();
            bound = true;
            //downloading info
            dataService.getContent();
            //showMessage(dataService.getStatus());
        }
        public void onServiceDisconnected(ComponentName className){
            bound = false;
        }
    };
    protected void onStart(){
        super.onStart();
        Intent intent = new Intent(this,timezoneservice.class);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);
    }
    protected void onStop(){
        super.onStop();
        if(bound){
            unbindService(connection);
        }
    }
   *////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Get the timezones of some cities from timezone.json file in zip
        //Add them in database it will work fine

        DBHelper db = new DBHelper(this);

        //Adding Data to Database if empty
        Intent intent = new Intent(this,timezoneservice.class);
        startService(intent);
//        if(db.getCountriesCount() == 0){
//            //timezone service being called
//
//       db.addCountry(new Country("Islamabad", "Asia/Karachi",false));
//            db.addCountry(new Country("New York", "America/New_York",false));
//            db.addCountry(new Country("Hawaii","Pacific/Tahiti",false));
//            db.addCountry(new Country("Karachi", "Asia/Karachi",false));
//            db.addCountry(new Country("Lahore", "Asia/Karachi",false));
//            db.addCountry(new Country("Bangkok", "Asia/Bangkok",false));
//            db.addCountry(new Country("Jakarta", "Asia/Jakarta",false));
//            db.addCountry(new Country("Kuwait", "Asia/Kuwait",false));
//       }

        // Reading all contacts
        Log.d("Reading: ", "Reading all countries..");
        List<Country> countries = db.getAllCountries();
        List<Country> selected_countries=new ArrayList<Country>();
        for (Country cn : countries) {
            if(cn.getSelected())
                selected_countries.add(cn);
        }



        RecyclerView recyclerViewDemo = findViewById(R.id.rv_favorites_list);
        recyclerViewDemo.setLayoutManager(new LinearLayoutManager(this));
        clockAdapter = new ClockAdapter(selected_countries, this);
        recyclerViewDemo.setAdapter(clockAdapter );

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Bundle bundle = new Bundle();
                //bundle.putSerializable("existing_country_list", (Serializable) selected_countries);

                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
               // intent.putExtras(bundle);
                startActivity(intent);
                //finish();
            }
        });

        final Handler handler = new Handler();
        handler.postDelayed( new Runnable() {

            @Override
            public void run() {
                clockAdapter.notifyDataSetChanged();
                handler.postDelayed( this,  1000 );
            }
        }, 1000 );
    }

        /*public void onPause(){
        super.onPause();
        PersistableCollection<Country> collection = new PersistableCollection(countries);
        collection.save(getApplicationContext());
    }

    public void onResume(){
        super.onResume();
        countries = new ArrayList<Country>();
        PersistableCollection<Country> collection = new PersistableCollection(countries);
        collection.load(getApplicationContext());
    }*/

    public String printTime(TimeZone tz){

        Calendar c = Calendar.getInstance(tz);
        String time = String.format("%02d" , c.get(Calendar.HOUR_OF_DAY))+":"+
                String.format("%02d" , c.get(Calendar.MINUTE))+":"+
                String.format("%02d" , c.get(Calendar.SECOND))+":"+
                String.format("%03d" , c.get(Calendar.MILLISECOND));

        //Check logcat and search time it will show you the time
        Log.d("TimeZone: ", tz.getDisplayName());
        Log.d("Time: ", time);
        return time;
    }
}

/*
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.timely.ClockAdapter;
import com.example.timely.CountryAdapter;
import com.example.timely.Country;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    List<Country> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();


        list = (List<Country>)this.getIntent().getSerializableExtra("country_list");

        if(list == null)
            list = new ArrayList<>();

        RecyclerView recyclerViewDemo = findViewById(R.id.rv_favorites_list);
        recyclerViewDemo.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDemo.setAdapter(new ClockAdapter(list, this));

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("existing_country_list", (Serializable) list);

                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                //finish();
            }
        });
    }
}*/