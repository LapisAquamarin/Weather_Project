package com.mobile.weatherproject3.View;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobile.weatherproject3.R;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterSearchLocation extends BaseAdapter {
    private List<Map<String, String>> items;
    private Activity activity;
    public AdapterSearchLocation(Activity activity,List<Map<String, String>> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.item_layout_location, null);
        TextView tvCityName = (TextView) view.findViewById(R.id.tvCityName);
        TextView tvTemp = (TextView) view.findViewById(R.id.tvTempature);

        tvCityName.setText(items.get(i).get("city_name"));
        tvTemp.setText(items.get(i).get("temp") + "°C");

        return view;
    }
}
