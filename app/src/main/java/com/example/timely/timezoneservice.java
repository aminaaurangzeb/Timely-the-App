package com.example.timely;

import android.app.Service;

import android.os.*;
import android.content.*;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class timezoneservice extends Service {

    private final IBinder binder = new LocalBinder();

    public void onCreate() {

    }

    public int onStartCommand(Intent intent,int flags,int startId){


        Toast.makeText(this,"Service starting",Toast.LENGTH_SHORT).show();
        //getContent();
        return START_NOT_STICKY;

    }

    public IBinder onBind(Intent intent){
        return null;
    }

    public class LocalBinder extends Binder{
        public timezoneservice getService(){
            return timezoneservice.this;
        }
    }
//    class RetrieveFeedTask extends AsyncTask<String, Void, Void> {
//
//        private Exception exception;
//
//        protected RSSFeed doInBackground(String... urls) {
//            try {
//
//                        URL url = new URL("https://api.timezonedb.com/v2.1/list-time-zone?key=HBZTNOMZZB1I&format=json");
//                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                        connection.connect();
//
//                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                        while( (line = reader.readLine()) != null ){
//                            content.append(line);
//                        }
//                        line = content.toString();
//                        int a=0;
//                    }
//                    catch (Exception e){
//                        line = "some error";
//                        e.printStackTrace();
//                        Log.i("TAG", "Error: " + e);
//                    }
//
//                    return line;
//        }
//
//        protected void onPostExecute(RSSFeed feed) {
//            // TODO: check this.exception
//            // TODO: do something with the feed
//        }
//    }

}
