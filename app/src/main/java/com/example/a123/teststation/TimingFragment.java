package com.example.a123.teststation;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TimingFragment extends Fragment implements OnItemRecyclerClick {

    private static final String TAG = TimingFragment.class.getSimpleName();
    private TimingAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private boolean flag; //переменная булевская для преключения между списком городов отправления и прибытия

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        flag = getArguments().getBoolean("flag");
        return inflater.inflate(R.layout.fragment_timing, container, false);
        //сделать по подобию https://stackoverflow.com/questions/12739909/send-data-from-activity-to-fragment-in-android
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        //List<Station> stationList = new ArrayList<>();

        Gson gson = new Gson();
        InputStream file = getActivity().getResources().openRawResource(R.raw.allstations);
        BufferedReader rd = new BufferedReader(new InputStreamReader(file));
        Type type = new TypeToken<CityTablo>() {
        }.getType();
//        Type type = new TypeToken<List<CityTablo>>() {}.getType();
        CityTablo citiTablo = gson.fromJson(rd, type);
//        List<Station>  allstations = gson.fromJson(rd, type);
        List<Station> allstations = new ArrayList<>();
        List<City> cities;
        if (flag) {
            Log.e(TAG, "getCitiesFrom" + citiTablo.getCitiesFrom().get(0).getStations().get(0).getStationTitle());
            cities = citiTablo.getCitiesFrom();
        } else {
            Log.e(TAG, "getCitiesTo" + citiTablo.getCitiesTo().get(0).getStations().get(0).getStationTitle());
            cities = citiTablo.getCitiesTo();
        }
        //List<City> cities = citiTablo.getCitiesFrom();
        for (City city : cities) {
            allstations.addAll(city.getStations());
            //Log.e(TAG, station.getStationTitle());
        }
        mAdapter = new TimingAdapter(allstations, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(int position, Station station) {
        Log.d("TimingFragment", Integer.toString(station.getStationId()));
    }
}