package com.ityadi.app.tourmate.Activity;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ityadi.app.tourmate.R;

public class TravelEventFragment extends Fragment {

    TextView tvDate;
    String selectedDate;

    public TravelEventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.travel_event_fragment, container, false);
        tvDate = (TextView) rootView.findViewById(R.id.tvDate);

        //final Button showDate = (Button) rootView.findViewById(R.id.btnShowDate);
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Dashboard) getActivity()).setDate();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        selectedDate = ((Dashboard) getActivity()).CUR_DATE;
                        tvDate.setText(selectedDate);
                    }
                }, 5000);
            }
        });


        ((Dashboard) getActivity()).fab.hide();
        return rootView;
    }



}
