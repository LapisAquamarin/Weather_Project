package com.mobile.weatherproject3.Control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobile.weatherproject3.Model.Weather;
import com.mobile.weatherproject3.R;
import com.mobile.weatherproject3.SearchLocation;
import com.mobile.weatherproject3.View.CustomAdapterMainActivity;
import com.mobile.weatherproject3.View.CustomAdapterRecycleViewMainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvMainActivity;
    Button btnSearchLocate;
    RecyclerView rcMainActivity;
    CustomAdapterMainActivity adapterWeatherMainActivity;
    CustomAdapterRecycleViewMainActivity adapterRecycleViewMainActivity;

    ArrayList<Weather> listWeatherGeneral = new ArrayList<>();
    ArrayList<Weather> listWeatherDetail = new ArrayList<>();
    private final String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" + "H%E1%BB%93%20Ch%C3%AD%20Minh?unitGroup=metric&key=CFRUQ3SPBAF8YZFZM7NJ5MYWB&contentType=json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControl();
        addEvent();
        getDataWeather(url);

    }
    private void addControl() {
        lvMainActivity = (ListView) findViewById(R.id.lvMainActivity);
        rcMainActivity = (RecyclerView) findViewById(R.id.rcMainActivity);
        btnSearchLocate = (Button) findViewById(R.id.btnSearchLocate);

    }
    private void addEvent() {
        btnSearchLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchLocation.class);
                startActivity(intent);
            }
        });
    }

    public void getDataWeather(String url)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    parseJsonData(response);
                    parseJsonDetailData(response);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    public void parseJsonData(String response) throws JSONException {
        JSONObject weatherOject = new JSONObject(response);
        Object weatherDays = weatherOject.get("days");
        JSONArray daysArray = (JSONArray) weatherDays;
        JSONObject weatherToday = daysArray.getJSONObject(0);
        Weather weather = new Weather();
        String cityName = weatherOject.getString("address");

        byte[] utf8Bytes = cityName.getBytes(StandardCharsets.UTF_8);
        String newString = new String(utf8Bytes, StandardCharsets.UTF_8);

        weather.setCityName(newString);
        weather.setWeather(weatherToday.getString("conditions"));
        weather.setTempature(weatherToday.getString("temp"));
        listWeatherGeneral.add(weather);

        adapterWeatherMainActivity = new CustomAdapterMainActivity(getApplicationContext(), R.layout.custom_listview_main_activity, listWeatherGeneral);
        lvMainActivity.setAdapter(adapterWeatherMainActivity);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void parseJsonDetailData(String response) throws JSONException {
        JSONObject weatherOject = new JSONObject(response);
        JSONArray weatherDays = (JSONArray) weatherOject.get("days");
        JSONObject weatherHours = (JSONObject) weatherDays.get(0);
        JSONArray weatherHoursFollowingDays = (JSONArray) weatherHours.get("hours");

        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false);
        //adapterRecycleViewMainActivity = new CustomAdapterRecycleViewMainActivity(listWeatherDetail);
        //rcMainActivity.setLayoutManager(linearLayoutManager);
        //rcMainActivity.setAdapter(adapterRecycleViewMainActivity);

        rcMainActivity.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        adapterRecycleViewMainActivity = new CustomAdapterRecycleViewMainActivity(listWeatherDetail);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rcMainActivity.setLayoutManager(mLayoutManager);
        rcMainActivity.setItemAnimator(new DefaultItemAnimator());
        rcMainActivity.setAdapter(adapterRecycleViewMainActivity);

        for (int i = 0; i < weatherHoursFollowingDays.length(); i++)
        {
            Weather w = new Weather();
            JSONObject weatherHourToday = weatherHoursFollowingDays.getJSONObject(i);
            w.setTime(weatherHourToday.getString("datetime"));
            w.setConditionDetail(weatherHourToday.getString("conditions"));
            w.setTempature(weatherHourToday.getString("temp"));
            listWeatherDetail.add(w);
            adapterRecycleViewMainActivity.notifyDataSetChanged();
        }
    }

}