package com.ityadi.app.tourmate.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ityadi.app.tourmate.ApiHelper.EventNameApi;
import com.ityadi.app.tourmate.Common.CommonMethod;
import com.ityadi.app.tourmate.Common.Config;
import com.ityadi.app.tourmate.Common.InternetConnection;
import com.ityadi.app.tourmate.Common.InternetConnectionHandler;
import com.ityadi.app.tourmate.Common.Network;
import com.ityadi.app.tourmate.R;
import com.ityadi.app.tourmate.Response.EventNameResponse;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MomentDetails extends Fragment {
    TextView momentIdTV,eventNameTV,headingTV,descriptionTV,expenseAmountTV,createdAtTV;
    ImageView photo;
    Button eventLocationWeather,btnAaddMoment,btnMomentList;

    public static String EVENT_ID;
    public String eventName;
    CommonMethod commonMethod;


    public MomentDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        //Internet checking for all Fragement
        if (!InternetConnection.checkConnection(getActivity())){
            startActivity(new Intent(getActivity(), InternetConnectionHandler.class));
        }
        View rootView = inflater.inflate(R.layout.fragment_moment_details, container, false);

        //get data from previous list and set in UI
        Bundle bundle = this.getArguments();
        EVENT_ID = bundle.getString("eventId");
        commonMethod = new CommonMethod();
        /////////////
        EventNameApi eventNameApi = Network.createService(EventNameApi.class);
        Call<EventNameResponse> call = eventNameApi.getEventName(Config.APP_KEY,EVENT_ID);
        call.enqueue(new Callback<EventNameResponse>() {
            @Override
            public void onResponse(Call<EventNameResponse> call, Response<EventNameResponse> response) {
                final EventNameResponse enResponseBody = response.body();
                if(enResponseBody != null) {
                    eventName = enResponseBody.getEvent_name();
                    eventNameTV.setText(eventName);
                }
            }
            @Override
            public void onFailure(Call<EventNameResponse> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
        //eventNameTV.setText(commonMethod.getEventName(EVENT_ID));
        ///////////////////
        momentIdTV = (TextView) rootView.findViewById(R.id.momentIdTV);
        eventNameTV = (TextView) rootView.findViewById(R.id.eventNameTV);
        headingTV = (TextView) rootView.findViewById(R.id.headingTV);
        descriptionTV = (TextView) rootView.findViewById(R.id.descriptionTV);
        expenseAmountTV = (TextView) rootView.findViewById(R.id.expenseAmountTV);
        createdAtTV = (TextView) rootView.findViewById(R.id.createdAtTV);
        photo = (ImageView) rootView.findViewById(R.id.photo);

        String createdAt = bundle.getString("createdAt");
        createdAt = createdAt.substring(8,10) + "-" + createdAt.substring(5, 7) + "-" + createdAt.substring(0, 4);

        momentIdTV.setText(bundle.getString("momentId"));
        headingTV.setText(bundle.getString("heading"));
        descriptionTV.setText(bundle.getString("description"));
        expenseAmountTV.setText(bundle.getString("expenseAmount"));
        createdAtTV.setText(createdAt);

        String profile_image_url = Config.BASE_URL+"tourmate/momentPhoto/small/" + bundle.getString("photo");
        Picasso.with(getContext()).load(profile_image_url).into(photo);

        //weather button onclick
        btnMomentList = (Button) rootView.findViewById(R.id.btnMomentList);
        btnMomentList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("eventId", EVENT_ID);

                Fragment fr = new MomentListFragment();
                fr.setArguments(bundle);
                FragmentManager frm = getFragmentManager();
                frm.beginTransaction().replace(R.id.fragment_container, fr).commit();
            }
        });

        ((Dashboard) getActivity()).setTitle("Moment Details");
        ((Dashboard) getActivity()).fab.hide();
        return rootView;
    }


}
