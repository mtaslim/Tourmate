package com.ityadi.app.tourmate.Common;

/**
 * Created by taslim on 8/20/2016.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ityadi.app.tourmate.R;

import java.util.List;

public class TravelEventAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> eventName;
    private final List<String> journeyDate;
    private final List<String> budgetAmount;

    public TravelEventAdapter(Activity context, List<String> eventName, List<String> journeyDate, List<String> budgetAmount) {
        super(context, R.layout.list_row, eventName);

        this.context = context;
        this.eventName = eventName;
        this.journeyDate = journeyDate;
        this.budgetAmount = budgetAmount;
    }
    private static class ViewHolder{
        TextView eventNameTV;
        TextView journeyDateTV;
        TextView budgetAmountTV;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.list_row, null, true);
            viewHolder = new ViewHolder();

            viewHolder.eventNameTV= (TextView) convertView.findViewById(R.id.eventNameTV);
            viewHolder.journeyDateTV= (TextView) convertView.findViewById(R.id.journeyDateTV);
            viewHolder.budgetAmountTV= (TextView) convertView.findViewById(R.id.budgetAmountTV);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.eventNameTV.setText(eventName.get(position));
        viewHolder.journeyDateTV.setText(journeyDate.get(position));
        viewHolder.budgetAmountTV.setText(budgetAmount.get(position));
        return convertView;

    }
}