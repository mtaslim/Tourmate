package com.ityadi.app.tourmate.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ityadi.app.tourmate.ApiHelper.MomentListApi;
import com.ityadi.app.tourmate.Common.Config;
import com.ityadi.app.tourmate.Common.InternetConnection;
import com.ityadi.app.tourmate.Common.InternetConnectionHandler;
import com.ityadi.app.tourmate.Common.Network;
import com.ityadi.app.tourmate.Common.SpreferenceHelper;
import com.ityadi.app.tourmate.R;
import com.ityadi.app.tourmate.Response.MomentListResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MomentListFragment extends Fragment {
    SpreferenceHelper spreferenceHelper;
    String userName;
    ListView list;


    public MomentListFragment() {
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
        View view = inflater.inflate(R.layout.moment_list, container, false);
        ((Dashboard) getActivity()).setTitle("Moment List");
        ((Dashboard) getActivity()).fab.hide();


        MomentListApi momentListApi = Network.createService(MomentListApi.class);
        Call<MomentListResponse> call = momentListApi.getMomentList(Config.APP_KEY,userName); //

        call.enqueue(new Callback<MomentListResponse>() {
            @Override
            public void onResponse(Call<MomentListResponse> call, Response<MomentListResponse> response) {
                final MomentListResponse momentlList = response.body();
                if(momentlList != null) {
                    List<String> heading = new ArrayList<String>();
                    List<String> photo = new ArrayList<String>();
                    List<String> expenseAmount = new ArrayList<String>();

                    int total = momentlList.getMomentList().size();

                    int i = 0;
                    for (i = 0; i < total; i++) {
                        heading.add(momentlList.getMomentList().get(i).getHeading());
                        photo.add(momentlList.getMomentList().get(i).getPhoto());
                        expenseAmount.add(momentlList.getMomentList().get(i).getExpenseAmount());
                    }

                   /* TravelEventAdapter adapter = new TravelEventAdapter(getActivity(), eventName, journeyDate, budgetAmount);
                    list = (ListView) getView().findViewById(R.id.travelEventListView);
                    list.setAdapter(adapter);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> a, View v, int position,
                                                long id) {

                            Bundle bundle = new Bundle();
                            bundle.putString("eventId", travelList.getTravelEvent().get(position).getId());
                            bundle.putString("eventName", travelList.getTravelEvent().get(position).getEventName());
                            bundle.putString("journeyDate", travelList.getTravelEvent().get(position).getJourneyDate());
                            bundle.putString("locationCoverage", travelList.getTravelEvent().get(position).getLocationCoverage());
                            bundle.putString("budgetAmount", travelList.getTravelEvent().get(position).getBudgetAmount());
                            bundle.putString("description", travelList.getTravelEvent().get(position).getDescription());
                            bundle.putString("createdAt", travelList.getTravelEvent().get(position).getCreatedAt());
                            bundle.putString("updatedAt", travelList.getTravelEvent().get(position).getUpdatedAt());

                            Fragment fr = new TravelEventDetails();
                            fr.setArguments(bundle);
                            FragmentManager frm = getFragmentManager();
                            frm.beginTransaction().replace(R.id.fragment_container, fr).commit();
                        }
                    });*/
                }
                /////
            }
            @Override
            public void onFailure(Call<MomentListResponse> call, Throwable t) {
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
