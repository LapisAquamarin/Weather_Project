package com.mobile.weatherproject3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobile.weatherproject3.Control.MainActivity;
import com.mobile.weatherproject3.Model.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class search_location extends AppCompatActivity {

    ImageView imgSearch;
    EditText edtSearch;
    TextView tvLocation_search, tvDateTime_search, tvAverageTemperature_search,
            tvTemperatureDetail_search, tvWeather_search, tvTest;
    FrameLayout fragmentSearch;
    ArrayList<Weather> listWeather = new ArrayList<>();

    ArrayList<String> lsSearchResult = new ArrayList<>();

    String location = "Ho Chi Minh";

    String fileName = "saved_locations.json";

    private String url = "https://weather.visualcrossing" +
            ".com/VisualCrossingWebServices/rest/services/timeline/" + location + "?unitGroup=metric" +
            "&key=CFRUQ3SPBAF8YZFZM7NJ5MYWB&contentType=json";

    Button btnSaveLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);
        addControls();
        getDataWeather(url);
        addEvents();
    }
    private void addControls(){
        edtSearch = (EditText) findViewById(R.id.edtLocation);
        tvLocation_search = (TextView) findViewById(R.id.tvLocation_search);
        tvDateTime_search = (TextView) findViewById(R.id.tvDateTime_search);
        tvAverageTemperature_search = (TextView) findViewById(R.id.tvAverageTemperature_search);
        tvTemperatureDetail_search = (TextView) findViewById(R.id.tvTemperatureDetail_search);
        tvWeather_search = (TextView) findViewById(R.id.tvWeather_search);
        fragmentSearch = (FrameLayout) findViewById(R.id.fragmentSearch);
        btnSaveLocation = (Button) findViewById(R.id.btnSaveLocation);
        imgSearch = (ImageView) findViewById(R.id.imgSearch);
        tvTest = (TextView) findViewById(R.id.tvTest);
    }
    private void addEvents(){
        edtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Toast.makeText(getApplicationContext(), "Has focus",Toast.LENGTH_LONG).show();
//                    LinearLayout.LayoutParams lp =
//                            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                                    500);
//                    LayoutTransition layoutTransition = new LayoutTransition();
//                    layoutTransition.setDuration(10000);
//                    layoutTransition.setInterpolator(LayoutTransition.CHANGING,
//                            new DecelerateInterpolator());
//                    fragmentSearch.setLayoutTransition(layoutTransition);
//                    fragmentSearch.setLayoutParams(lp);


                } else {
//                    LinearLayout.LayoutParams lp =
//                            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                                    0);
//                    LayoutTransition layoutTransition = new LayoutTransition();
//                    layoutTransition.setDuration(10000);
//                    layoutTransition.setInterpolator(LayoutTransition.CHANGING,
//                            new DecelerateInterpolator());
//                    fragmentSearch.setLayoutTransition(layoutTransition);
//                    fragmentSearch.setLayoutParams(lp);
                    Toast.makeText(getApplicationContext(), "Lost focus",Toast.LENGTH_LONG).show();
                }
            }
        });


        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                location = edtSearch.getText().toString();
                url = "https://weather.visualcrossing" +
                        ".com/VisualCrossingWebServices/rest/services/timeline/" + location + "?unitGroup=metric" +
                        "&key=CFRUQ3SPBAF8YZFZM7NJ5MYWB&contentType=json";
                getDataWeather(url);

                edtSearch.clearFocus();

//                String search_url = "https://api.locationiq.com/v1/autocomplete?key=pk" +
//                        ".fb0180fcdfefb85427c278082fb48a28&q=" + location + "&limit=5";
//                getSearchResult(search_url);
//                loadFragment(new fragment_search());
            }
        });

        btnSaveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFile(tvLocation_search.getText().toString());
                Intent intent = new Intent(search_location.this, MainActivity.class);

                intent.putExtra("location", edtSearch.getText());
                startActivity(intent);
                Toast.makeText(getApplicationContext(),
                        "Location saved: " + tvLocation_search.getText().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

//    public void getSearchResult(String url) {
//        RequestQueue requestQueue = Volley.newRequestQueue(search_location.this);
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    parseJsonSearchData(response);
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error){
//                Toast.makeText(search_location.this, "Error", Toast.LENGTH_LONG).show();
//            }
//        });
//        requestQueue.add(stringRequest);
//    }

    public void getDataWeather(String url)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(search_location.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    parseJsonData(response);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(search_location.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    @SuppressLint("SetTextI18n")
    public void parseJsonData(String response) throws JSONException {
        JSONObject weatherObject = new JSONObject(response);
        Object weatherDays = weatherObject.get("days");
        JSONArray daysArray = (JSONArray) weatherDays;
        JSONObject weatherToday = daysArray.getJSONObject(0);
        Weather weather = new Weather();
        String cityName = weatherObject.getString("address");

        weather.setCityName(cityName);
        weather.setWeather(weatherToday.getString("conditions"));
        weather.setTempature(weatherToday.getString("temp"));
        listWeather.add(weather);

        tvLocation_search.setText(cityName);
        tvDateTime_search.setText(weatherToday.getString("datetime"));
        tvAverageTemperature_search.setText(weatherToday.getString("temp"));
        tvTemperatureDetail_search.setText(weatherToday.getString("tempmin") + " - " + weatherToday.getString("tempmax"));
        tvWeather_search.setText(weatherToday.getString("conditions"));

    }


    public void writeToFile(JSONArray jsonArray) {
        File file = new File(this.getFilesDir(), fileName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            bufferedWriter.write(jsonArray.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONArray readFromFile() {
        File file = new File(this.getFilesDir(), fileName);
        if (!file.exists()) {
            try {
                //JSON file created at /data/user/0/com.mobile.weatherproject3/files/saved_locations.json
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
            return new JSONArray(stringBuilder.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addToFile(String obj) {
        JSONArray jsonArray = readFromFile();
        if (jsonArray == null) {
            jsonArray = new JSONArray();
        }
        jsonArray.put(obj);
        writeToFile(jsonArray);
    }


//    public void parseJsonSearchData(String response) throws JSONException {
//        JSONObject searchObj = new JSONObject(response);
//        JSONArray searchArray = new JSONArray(response);
//        Log.d("JSONArray", "" + searchArray);
//
//        for (int i = 0;i < searchArray.length(); i++){
//            JSONObject obj = searchArray.getJSONObject(i);
//            Log.d("display_name", "" + obj.getString("display_name"));
//            if (!lsSearchResult.contains(obj.getString("display_name"))){
//                lsSearchResult.add(obj.getString("display_name"));
//            }
//        }
//        Log.d("List location", "" + lsSearchResult);
//        Bundle bundle = new Bundle();
//        bundle.putStringArrayList("search_result", lsSearchResult);
//        fragment_search frgObj = new fragment_search();
//        frgObj.setArguments(bundle);
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.replace(R.id.fragmentSearch, new fragment_search());
//        ft.commit();
//    }

//    public void loadFragment(Fragment fragment){
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.replace(R.id.fragmentSearch, fragment);
//        ft.commit();
//    }
}