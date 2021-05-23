package com.example.timely;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SearchView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class UpdateActivity extends AppCompatActivity {


    List<Country> list;
    private CountryAdapter testAdapter;
    Country country;
    CheckBox check;
    List<Country> checkedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        list=new ArrayList<Country>();

        DBHelper db = new DBHelper(this);
        list = db.getAllCountries();

     /*   Intent intent = getIntent();
       checkedList = (ArrayList<Country>) intent.getSerializableExtra("existing_country_list");
        if(checkedList.size() == 0){

            checkedList=new ArrayList<Country>();

        }
        else{
            int i=0;
            for (Country c : list) {
                if (checkedList.contains(c))
                    list.get(i).setSelected(true);
                i++;
            }

        }*/

        RecyclerView recyclerViewDemo = findViewById(R.id.rv_country_list);

        recyclerViewDemo.setLayoutManager(new LinearLayoutManager(this));

        if(savedInstanceState!=null){
            list=(List<Country>)savedInstanceState.getSerializable("checked_country_list");
            System.out.println(list);

        }
        testAdapter = new CountryAdapter(list, this);

        recyclerViewDemo.setAdapter(testAdapter);

        Button btn = findViewById(R.id.btn_favourite_save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Country c : list) {
//                    if (c.getSelected() && checkedList.contains(c)==false)
//                        checkedList.add(c);
//                    else if(!c.getSelected() && checkedList.contains(c)==true)
//                        checkedList.remove(c);
                    db.updateCountry(c);
                }
                Bundle bundle = new Bundle();
//                bundle.putSerializable("country_list", (Serializable) checkedList);
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
//                intent.putExtras(bundle);
                //finish();
                startActivity(intent);
            }
        });

        SearchView editsearch = (SearchView) findViewById(R.id.simpleSearchView);
        editsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String text = newText;
                testAdapter.filter(text);
                return false;
            }
        });
    }
//
//    protected void onRestoreInstanceState (Bundle savedInstanceState) {
//        super.onRestoreInstanceState (savedInstanceState);
//
//    }

    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        System.out.println(list);
        outState.putSerializable("checked_country_list", (Serializable) list);
    }

    public void checkboxClick(View v){
        if(country != null){

            boolean checked = ((CheckBox) v).isChecked();
            country.setSelected(checked);

        }
    }

}


