package com.example.timely;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timely.R;
import com.example.timely.Country;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;


public class ClockAdapter extends RecyclerView.Adapter<ClockAdapter.PostViewHolder>{

    private List<Country> countryList;
    private Context        mContext;

    public ClockAdapter(List<Country> PostItems, Context mContext) {
        this.countryList   = PostItems;
        this.mContext     = mContext;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.adapter_clock,parent,false);
        PostViewHolder PostViewHolder = new PostViewHolder(v);
        return PostViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

        String name = countryList.get(position).getName();
        holder.name.setText(name);
        String time = countryList.get(position).getTime();
        TimeZone tz = TimeZone.getTimeZone(time);
        holder.day.setText(printDay(tz));
        holder.time.setText(printTime(tz));
    }
    public String printDay(TimeZone tz){

        Calendar c = Calendar.getInstance(tz);
        String time = String.format("%s" ,getDayName(c.get(Calendar.DAY_OF_WEEK)));
        return time;
    }
    public String printTime(TimeZone tz){

        Calendar c = Calendar.getInstance(tz);
        String time =
                String.format("%02d" , c.get(Calendar.HOUR_OF_DAY))+":"+
                String.format("%02d" , c.get(Calendar.MINUTE));
        return time;
    }
    String getDayName(int dayOfWeek){
        switch(dayOfWeek){
            case 1: return "Sunday";
            case 2: return "Monday";
            case 3: return "Tuesday";
            case 4: return "Wednesday";
            case 5: return "Thursday";
            case 6: return "Friday";
            case 7: return "Saturday";
            default: return "";
        }
    }
    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView time,day;

        PostViewHolder(@NonNull final View itemView) {
            super(itemView);

            name =  itemView.findViewById(R.id.tv_favorite_country);
            time =  itemView.findViewById(R.id.tv_favorite_country_time);
            day =  itemView.findViewById(R.id.tv_day);
        }
    }
}

