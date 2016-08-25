package com.ityadi.app.tourmate.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ityadi.app.tourmate.Common.CommonMethod;
import com.ityadi.app.tourmate.Common.DisableUi;
import com.ityadi.app.tourmate.Common.InternetConnection;
import com.ityadi.app.tourmate.Common.InternetConnectionHandler;
import com.ityadi.app.tourmate.R;


public class TravelEventDetails extends Fragment {
    public static String EVENT_ID="0";
    public static String EVENT_LOCATION;

    EditText etEventName,etLocationCoverage,etBudgetAmount,etDescription;
    Button eventLocationWeather,btnAaddMoment,btnMomentList;
    TextView etJourneyDate;
    TextView tvCreatedAt;
    String selectedDate;
    DisableUi disableUi;
    CommonMethod commonMethod;
    int dateEditable=2;

    public TravelEventDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        disableUi = new DisableUi();
        commonMethod = new CommonMethod();

        //Internet checking for all Fragement
        if (!InternetConnection.checkConnection(getActivity())){
            startActivity(new Intent(getActivity(), InternetConnectionHandler.class));
        }
        View rootView = inflater.inflate(R.layout.fragment_travel_event_details, container, false);

        //get data from previous list and set in UI
        Bundle bundle = this.getArguments();

        EVENT_ID = bundle.getString("eventId");
        EVENT_LOCATION =  bundle.getString("locationCoverage");

        etEventName = (EditText) rootView.findViewById(R.id.etEventName);
        etJourneyDate = (TextView) rootView.findViewById(R.id.etJourneyDate);
        etLocationCoverage = (EditText) rootView.findViewById(R.id.etLocationCoverage);
        etBudgetAmount = (EditText) rootView.findViewById(R.id.etBudgetAmount);
        etDescription = (EditText) rootView.findViewById(R.id.etDescription);
        tvCreatedAt = (TextView) rootView.findViewById(R.id.tvCreatedAt);

        String journeyDate = bundle.getString("journeyDate");
        String createdAt = bundle.getString("createdAt");

        journeyDate = journeyDate.substring(8) + "-" + journeyDate.substring(5, 7) + "-" + journeyDate.substring(0, 4);
        createdAt = createdAt.substring(8,10) + "-" + createdAt.substring(5, 7) + "-" + createdAt.substring(0, 4);

        etEventName.setText(bundle.getString("eventName"));
        etJourneyDate.setText(journeyDate);
        etLocationCoverage.setText(bundle.getString("locationCoverage"));
        etBudgetAmount.setText(bundle.getString("budgetAmount"));
        etDescription.setText(bundle.getString("description"));
        tvCreatedAt.setText(createdAt);

        disableUi.disableEditText(etBudgetAmount);
        disableUi.disableTextView(tvCreatedAt);

        int dateEnableDisable = commonMethod.dateEnableDisable(bundle.getString("journeyDate"));
        if(dateEnableDisable == 0) disableUi.disableTextView(etJourneyDate);

        // Date Set if date lesthan current date
        etJourneyDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Dashboard) getActivity()).setDate();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        selectedDate = ((Dashboard) getActivity()).CUR_DATE;
                        etJourneyDate.setText(selectedDate);
                    }
                }, 5000);
            }
        });


        //weather button onclick
        eventLocationWeather = (Button) rootView.findViewById(R.id.eventLocationWeather);
        eventLocationWeather.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Test", Toast.LENGTH_LONG).show();
                Bundle bundle = new Bundle();
                bundle.putString("location", EVENT_LOCATION);

                Fragment fr = new WeatherFragment();
                fr.setArguments(bundle);
                FragmentManager frm = getFragmentManager();
                frm.beginTransaction().replace(R.id.fragment_container, fr).commit();
            }
        });

        //add moment button onclick
        btnAaddMoment = (Button) rootView.findViewById(R.id.btnAaddMoment);
        btnAaddMoment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(Integer.parseInt(EVENT_ID) > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putString("eventId", EVENT_ID);

                    Fragment fr = new FragmentMoment();
                    fr.setArguments(bundle);
                    FragmentManager frm = getFragmentManager();
                    frm.beginTransaction().replace(R.id.fragment_container, fr).commit();
                }
            }
        });

        //add moment button onclick
        btnMomentList = (Button) rootView.findViewById(R.id.btnMomentList);
        btnMomentList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    Fragment fr = new MomentListFragment();
                    FragmentManager frm = getFragmentManager();
                    frm.beginTransaction().replace(R.id.fragment_container, fr).commit();
            }
        });

        ((Dashboard) getActivity()).setTitle("Travel Event Details");
        ((Dashboard) getActivity()).fab.hide();
        return rootView;
    }


}
