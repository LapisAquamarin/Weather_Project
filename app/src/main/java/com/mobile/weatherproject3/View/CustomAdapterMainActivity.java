package com.mobile.weatherproject3.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mobile.weatherproject3.Model.Weather;
import com.mobile.weatherproject3.R;

import java.util.ArrayList;

public class CustomAdapterMainActivity extends ArrayAdapter {
    Context context;
    int layoutItem;
    ArrayList<Weather> arrayList = new ArrayList<>();


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Weather weather = arrayList.get(position);
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(layoutItem, null);
        }
        TextView tvCity = (TextView) convertView.findViewById(R.id.tvCity);
        tvCity.setText(weather.getCityName());
        TextView tvWeather = (TextView) convertView.findViewById(R.id.tvWeather);
        tvWeather.setText(weather.getWeather());
        TextView tvTempature = (TextView) convertView.findViewById(R.id.tvTempature);
        tvTempature.setText(weather.getTempature());

        return convertView;
    }
    public CustomAdapterMainActivity(@NonNull Context context, int resource, ArrayList<Weather> weatherArrayList) {
        super(context, resource, weatherArrayList);
        this.context = context;
        this.layoutItem = resource;
        this.arrayList = weatherArrayList;
    }
}
