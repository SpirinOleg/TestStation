package com.example.a123.teststation;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TimingAdapter extends RecyclerView.Adapter<TimingAdapter.ViewHolder> {

    private static final String TAG = TimingFragment.class.getSimpleName();
    private List<Station> stations = new ArrayList<>();
    private OnItemRecyclerClick listener;

    TimingAdapter(List<Station> data, OnItemRecyclerClick listener){
        stations = data;
        this.listener = listener;
    }


    @Override
    public TimingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TimingAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Log.e(TAG, stations.get(position).getStationTitle());
        holder.citiesName.setText(stations.get(position).getStationTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                listener.onClick(position, stations.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.e(TAG, Integer.toString(stations.size()));
        return stations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView citiesName;

        public ViewHolder(View itemView) {
        super(itemView);
            citiesName = (TextView) itemView.findViewById(R.id.stationName);
//            citiesTo = (TextView) itemView.findViewById(R.id.citiesTo);
        }

//        void stationCiti(Station station) {
//            citiesFrom.setText("Станция отправления: " + station.getCitiesFrom());
//            citiesTo.setText("Станция прибытия:" + Station.getCitiesTo());
//        }
    }
}
