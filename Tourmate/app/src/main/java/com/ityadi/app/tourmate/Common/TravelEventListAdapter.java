package com.ityadi.app.tourmate.Common;
/*
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ityadi.app.tourmate.R;
import com.ityadi.app.tourmate.Response.CurrentWeatherArrayList;


public class TravelEventListAdapter extends BaseAdapter {
  private Context context;
    private CurrentWeatherArrayList allTravel;
    private final LayoutInflater mInflater;

    public TravelEventListAdapter(Context context, CurrentWeatherArrayList allTravel) {
        super(context, R.layout.list_row,allTravel);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context=context;
        this.allTravel=allTravel;
    }


    private static class ViewHolder{
        TextView eventNameTV;
        TextView journeyDateTV;
        TextView budgetAmountTV;
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.list_row,null,true);
            viewHolder = new ViewHolder();

            viewHolder.eventNameTV= (TextView) convertView.findViewById(R.id.eventNameTV);
            viewHolder.journeyDateTV= (TextView) convertView.findViewById(R.id.journeyDateTV);
            viewHolder.budgetAmountTV= (TextView) convertView.findViewById(R.id.budgetAmountTV);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.eventNameTV.setText(allTravel.getTravelEvent().get(position).getEventName());
        viewHolder.journeyDateTV.setText(allTravel.getTravelEvent().get(position).getJourneyDate());
        viewHolder.budgetAmountTV.setText(allTravel.getTravelEvent().get(position).getBudgetAmount());


        return convertView;
    }

}
*/