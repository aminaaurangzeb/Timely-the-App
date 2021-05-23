package com.example.timely;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.example.timely.R;
import com.example.timely.Country;

import static java.security.AccessController.getContext;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.PostViewHolder>{

        private List<Country> countryList;
        private Context mContext;
        private ArrayList<Country> arraylist;

        public CountryAdapter(List<Country> countryList, Context mContext) {
            this.countryList = countryList;
            this.mContext     = mContext;
            this.arraylist = new ArrayList<>();
            this.arraylist.addAll(countryList);
        }

        @NonNull
        @Override
        public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.adapter_country,parent,false);
            PostViewHolder PostViewHolder = new PostViewHolder(v);
            return PostViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Country c=countryList.get(position);
            String name = countryList.get(position).getName();

            holder.name.setText(name);

            String time = countryList.get(position).getTime();
//            holder.time.setText(time);
            TimeZone tz = TimeZone.getTimeZone(time);
            holder.time.setText(printTime(tz));

            holder.checkBox.setTag(Integer.valueOf(position)); // set the tag so we can identify the correct row in the listener
            holder.checkBox.setChecked( countryList.get(position).getSelected()); // set the status as we stored it
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    countryList.get(position).setSelected(isChecked); // get the tag so we know the row and store the status
                //   notifyDataSetChanged();
                }
            });

            holder.delButton.setTag(c);
            holder.delButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    countryList.remove(c);
                    arraylist.remove(c);
                    DBHelper dbHelper = new DBHelper(mContext);
                   dbHelper.deleteCountry(c);
                    // cn.delete(dbHelper.getWritableDatabase());
                    //notifyDataSetChanged();
                    notifyItemRemoved(position);
                }
            });

        }
    public String printTime(TimeZone tz){

        Calendar c = Calendar.getInstance(tz);
        String time = String.format("%s" ,getDayName(c.get(Calendar.DAY_OF_WEEK)))+","+
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
            private TextView time;
            private CheckBox checkBox;
           private ImageButton delButton;
//        ItemClickListener itemClickListener;


            PostViewHolder(@NonNull final View itemView) {
                super(itemView);

                checkBox =  itemView.findViewById(R.id.cb_country);
                name =  itemView.findViewById(R.id.tv_country_name);
                time =  itemView.findViewById(R.id.tv_country_time);
               delButton=itemView.findViewById(R.id.del_country);
//            itemView.setOnClickListener(this);
            }


//        public void onClick(View view) {
//            this.itemClickListener.onItemClickListener(getLayoutPosition());
//        }
//
//        void setItemClickListener(ItemClickListener ic)
//        {
//            this.itemClickListener = ic;
//        }


        }

        // Filter Class
        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            countryList.clear();
            if (charText.length() == 0) {
                countryList.addAll(arraylist);
            } else {
                for (Country wp : arraylist) {
                    if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                        countryList.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        }


}
