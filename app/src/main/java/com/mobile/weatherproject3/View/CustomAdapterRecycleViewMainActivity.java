package com.mobile.weatherproject3.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.weatherproject3.Model.Weather;
import com.mobile.weatherproject3.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class CustomAdapterRecycleViewMainActivity extends RecyclerView.Adapter<CustomAdapterRecycleViewMainActivity.ViewHolder> {

    private ArrayList<Weather> weatherList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recycleview_main_activity,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Weather weather = weatherList.get(position);
        holder.time.setText(weather.getTime());
        holder.tempature.setText(weather.getTempature());
        holder.condition.setText(weather.getConditionDetail());
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public CustomAdapterRecycleViewMainActivity(ArrayList<Weather> weatherList)
    {
        this.weatherList = weatherList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView time, condition, tempature;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.tvTime);
            condition = (TextView) itemView.findViewById(R.id.tvCondition);
            tempature = (TextView) itemView.findViewById(R.id.tvTempatureRV);
        }
    }
}
