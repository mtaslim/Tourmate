package com.ityadi.app.tourmate.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ityadi.app.tourmate.ApiHelper.TravelEventListApi;
import com.ityadi.app.tourmate.Common.Config;
import com.ityadi.app.tourmate.Common.CustomListAdapter;
import com.ityadi.app.tourmate.Common.InternetConnection;
import com.ityadi.app.tourmate.Common.InternetConnectionHandler;
import com.ityadi.app.tourmate.Common.Network;
import com.ityadi.app.tourmate.Common.SpreferenceHelper;
import com.ityadi.app.tourmate.R;
import com.ityadi.app.tourmate.Response.Example;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TravelEventListFragment extends Fragment {
    SpreferenceHelper spreferenceHelper;
    String userName;
    ListView list;


    public TravelEventListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        spreferenceHelper = new SpreferenceHelper(getActivity());
        userName = spreferenceHelper.retrive();
        //Internet checking for all Fragement
        if (!InternetConnection.checkConnection(getActivity())){
            startActivity(new Intent(getActivity(), InternetConnectionHandler.class));
        }
        View view = inflater.inflate(R.layout.travel_event_list_fragment, container, false);
        ((Dashboard) getActivity()).fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new TravelEventFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });
        ((Dashboard) getActivity()).setTitle("Travel Event List");
        ((Dashboard) getActivity()).fab.show();


        TravelEventListApi travelEventListApi = Network.createService(TravelEventListApi.class);
        Call<Example> call = travelEventListApi.getTravelList(Config.APP_KEY,userName); //

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                Example travelList = response.body();
                if(travelList != null) {
                    List<String> eventName = new ArrayList<String>();
                    List<String> journeyDate = new ArrayList<String>();
                    List<String> budgetAmount = new ArrayList<String>();

                    int total = travelList.getTravelEvent().size();

                    int i = 0;
                    for (i = 0; i < total; i++) {
                        eventName.add(travelList.getTravelEvent().get(i).getEventName());
                        budgetAmount.add(travelList.getTravelEvent().get(i).getBudgetAmount());
                        String myDate = travelList.getTravelEvent().get(i).getJourneyDate();

                        String myConvertedDate = myDate.substring(8) + "-" + myDate.substring(5, 7) + "-" + myDate.substring(0, 4);
                        journeyDate.add(myConvertedDate);
                    }

                    CustomListAdapter adapter = new CustomListAdapter(getActivity(), eventName, journeyDate, budgetAmount);
                    list = (ListView) getView().findViewById(R.id.travelEventListView);
                    list.setAdapter(adapter);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // String Slecteditem = itemname[+position];
                            // Toast.makeText(getActivity(), Slecteditem, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                /////
            }
            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });



        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }






}
