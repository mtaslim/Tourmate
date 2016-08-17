package com.ityadi.app.tourmate.Common;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by taslim on 8/16/2016.
 */
public class InternetConnection {

    /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
    public static boolean checkConnection(Context context) {
        return  ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}
