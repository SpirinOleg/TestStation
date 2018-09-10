package com.example.a123.teststation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_station_from).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showStaionFrom();
            }
        });

        view.findViewById(R.id.btn_station_to).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showStaionTo();
            }
        });

    }

    private void showStaionFrom(){
        Bundle bundle = new Bundle();
        bundle.putBoolean("flag", true);

        Fragment timingFragment = new TimingFragment();
        timingFragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, timingFragment);

        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showStaionTo(){
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
