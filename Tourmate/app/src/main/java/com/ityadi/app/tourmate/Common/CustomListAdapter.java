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

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> eventName;
    private final List<String> journeyDate;
    private final List<String> budgetAmount;

    public CustomListAdapter(Activity context, List<String> eventName, List<String> journeyDate, List<String> budgetAmount) {
        super(context, R.layout.list_row, eventName);

        this.context = context;
        this.eventName = eventName;
        this.journeyDate = journeyDate;
        this.budgetAmount = budgetAmount;
    }


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_row, null, true);

        TextView eventNameTV = (TextView) rowView.findViewById(R.id.eventNameTV);
        TextView journeyDateTV = (TextView) rowView.findViewById(R.id.journeyDateTV);
        TextView budgetAmountTV = (TextView) rowView.findViewById(R.id.budgetAmountTV);

        eventNameTV.setText(eventName.get(position));
        journeyDateTV.setText(journeyDate.get(position));
        budgetAmountTV.setText(budgetAmount.get(position));
        return rowView;

    }
}