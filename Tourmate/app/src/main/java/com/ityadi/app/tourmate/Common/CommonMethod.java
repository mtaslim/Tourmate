package com.ityadi.app.tourmate.Common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by taslim on 8/23/2016.
 */
public class CommonMethod {
    public int  dateEnableDisable(String compareableDate){
        int dateEnableDisable = 0; // initialize disable

        //compareableDate yyyy-mm-dd to dd/mm/yyyy
        compareableDate = compareableDate.substring(8) + "/" +
                compareableDate.substring(5, 7) + "/" +
                compareableDate.substring(0, 4);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);  // number of days to reduce
        String yesterday = (String)(sdf.format(c.getTime()));

        Date givenDate = null;
        Date strYesterday = null;
        try {
            givenDate = sdf.parse(compareableDate);
            strYesterday = sdf.parse(yesterday);

            if (strYesterday.after(givenDate)) dateEnableDisable = 0;
            else dateEnableDisable = 1;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateEnableDisable;
    }

    public String dateConvert(Date dateTime) {
        String string = dateTime+"";
        String[] parts = string.split(" ");
        return parts[0]+" "+parts[1]+" "+parts[2];
    }
    public String timeConvert(Date dateTime) {
        String string = dateTime+"";
        String[] parts = string.split(" ");
        return parts[3];
    }
    public String weatherDateTime(Date dateTime) {
        DateFormat df = new SimpleDateFormat("E,  h:mm a");
        String sdt = df.format(dateTime);
        return sdt;
    }


}
