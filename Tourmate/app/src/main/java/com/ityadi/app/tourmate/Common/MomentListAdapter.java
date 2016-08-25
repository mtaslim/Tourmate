package com.ityadi.app.tourmate.Common;

/**
 * Created by taslim on 8/20/2016.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ityadi.app.tourmate.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MomentListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> heading;
    private final List<String> photo;
    private final List<String> expenseAmount;

    public MomentListAdapter(Activity context, List<String> heading, List<String> photo, List<String> expenseAmount) {
        super(context, R.layout.moment_list_row, heading);

        this.context = context;
        this.heading = heading;
        this.photo = photo;
        this.expenseAmount = expenseAmount;
    }
    private static class ViewHolder{
        TextView headingTV;
        ImageView photoImage;
        TextView expenseAmountTV;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.moment_list_row, null, true);
            viewHolder = new ViewHolder();

            viewHolder.headingTV= (TextView) convertView.findViewById(R.id.headingTV);
            viewHolder.photoImage= (ImageView) convertView.findViewById(R.id.photoImage);
            viewHolder.expenseAmountTV= (TextView) convertView.findViewById(R.id.expenseAmountTV);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String profile_image_url = Config.BASE_URL+"tourmate/momentPhoto/small/" + photo.get(position);
        Picasso.with(getContext()).load(profile_image_url).into(viewHolder.photoImage);

        viewHolder.headingTV.setText(heading.get(position));
        viewHolder.expenseAmountTV.setText(expenseAmount.get(position));
        return convertView;

    }
}