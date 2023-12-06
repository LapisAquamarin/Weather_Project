package com.mobile.weatherproject3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.mobile.weatherproject3.Control.MainActivity;
import com.mobile.weatherproject3.View.AdapterSearchLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchLocation extends AppCompatActivity {
    ImageButton btnBack;
    ListView lvLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);
        addControls();
        addEvents();
        // Test Data on list view
        List<Map<String, String>> listItem= new ArrayList<>();

        Map<String, String> mapValue= new HashMap<>();
        mapValue.put("city_name", "Ho Chi Minh");
        mapValue.put("temp", "30");
        mapValue.put("current_condition", "Mostly Cloudy");
        mapValue.put("highest_temp","32");
        mapValue.put("lowest_temp","25");

        Map<String, String> mapValue2= new HashMap<>();
        mapValue2.put("city_name", "Ha Noi");
        mapValue2.put("temp", "28");
        mapValue2.put("current_condition", "Stormy");
        mapValue2.put("highest_temp","33");
        mapValue2.put("lowest_temp","24");

        System.out.println(mapValue);
        listItem.add(mapValue);
        listItem.add(mapValue2);

        AdapterSearchLocation adapter = new AdapterSearchLocation(this, listItem);
        lvLocation.setAdapter(adapter);
    }

    void addControls(){
        lvLocation = (ListView) findViewById(R.id.lvLocation);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
    }

    void addEvents(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (SearchLocation.this, MainActivity.class);
                startActivity(intent);
            }
        });

//        lvLocation.setOnClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                System.out.println(adapterView.getItemAtPosition(i));
//            }
//        });

    }
}