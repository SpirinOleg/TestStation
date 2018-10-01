package com.example.a123.teststation;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class TimingFragment extends Fragment implements OnItemRecyclerClick {

    private static final String TAG = TimingFragment.class.getSimpleName();
    private TimingAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    private StationAsyncTask task;


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
        //setHasOptionsMenu(true);
        //Необходимо для кнопки обновления в меню
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setProgress(0);


        mRecyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onResume() {
        super.onResume();
        task = new StationAsyncTask();
        task.execute();
    }

    @Override
    public void onPause() {
        super.onPause();
        task.cancel(true);
    }

    //Необходимо для кнопки обновления в меню
/*    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_timing_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.update){
            new StationAsyncTask().execute();
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onClick(int position, Station station) {
        Log.d("TimingFragment", Integer.toString(station.getStationId()));
    }

    public class StationAsyncTask extends AsyncTask<String, Integer, List<Station>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e(TAG, "Задача запущена");
            progressBar.setVisibility(View.VISIBLE);
            toast("Задача запущена");
        }

        @Override
        protected List<Station> doInBackground(String... strings) {
            List<Station> allstations = new LinkedList<>();
            getActivity().runOnUiThread(() -> toast("Начиню бессмыссленную работу"));
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
                publishProgress(i);
            }
            getActivity().runOnUiThread(() -> toast("Начиню полезную работу"));
            try {
                InputStream file = getActivity().getResources().openRawResource(R.raw.allstations);
                BufferedReader rd = new BufferedReader(new InputStreamReader(file));
                Gson gson = new Gson();
//        Type type = new TypeToken<CityTablo>() {}.getType();
                CityTablo citiTablo = gson.fromJson(rd, CityTablo.class);
                List<City> cities;
                if (flag) {
                    Log.e(TAG, "getCitiesFrom" + citiTablo.getCitiesFrom().get(0).getStations().get(0).getStationTitle());

                    cities = citiTablo.getCitiesFrom();
                } else {
                    Log.e(TAG, "getCitiesTo" + citiTablo.getCitiesTo().get(0).getStations().get(0).getStationTitle());
                    cities = citiTablo.getCitiesTo();
                }
                for (City city : cities) {
                    allstations.addAll(city.getStations());
                }
            } catch (Exception e){
                getActivity().runOnUiThread(() -> toast("Ошибка импорта json"));
            }
            return allstations;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(List<Station> stations) {
            super.onPostExecute(stations);
            progressBar.setVisibility(View.INVISIBLE);
            mAdapter = new TimingAdapter(stations, TimingFragment.this);
            mRecyclerView.setAdapter(mAdapter);
            Log.e(TAG, "Задача завершена");
            toast("Задача завершена");
        }

        @Override
        protected void onCancelled() {
            Log.e(TAG, "onCancelled()");
            toast("Операция отменена");
            super.onCancelled();
        }

        private void toast(String message) {
            Activity activity = getActivity();
            if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
