package com.example.a123.teststation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class MainFragment extends Fragment {

    public static final String STATION_NAME = "stationName";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText departureStation = view.findViewById(R.id.departureStationAddress);
        EditText arrivalStation = view.findViewById(R.id.arrivalStationAddress);

        view.findViewById(R.id.departureStationAddress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showStaionFrom(view);
                //departureStation.setText();
                //Здесь получить станцию через SP
                //После сохранить значение
                // Editor e = sp.edit();
                //e.putString("station", station);
                //e.commit();
                //использовать метод getDefaultSharedPreferences()
                //https://javadevblog.com/shared-preferences-v-android-sohranyaem-nastrojki.html
            }
        });

        view.findViewById(R.id.arrivalStationAddress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showStaionTo(view);
                //arrivalStation.setText();
                //Здесь получить станцию через SP
                //После сохранить значение
                // Editor e = sp.edit();
                //e.putString("station", station);
                //e.commit();
                //использовать метод getDefaultSharedPreferences()
                //https://javadevblog.com/shared-preferences-v-android-sohranyaem-nastrojki.html
            }
        });

    }



    private void showStaionFrom(View view){
        Bundle bundle = new Bundle();
        bundle.putBoolean("flag", true);

        Fragment timingFragment = new TimingFragment();
        timingFragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, timingFragment);

        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showStaionTo(View view){
        Bundle bundle = new Bundle();
        bundle.putBoolean("flag", false);

        Fragment timingFragment = new TimingFragment();
        timingFragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, timingFragment);

        transaction.addToBackStack(null);
        transaction.commit();
    }



}
