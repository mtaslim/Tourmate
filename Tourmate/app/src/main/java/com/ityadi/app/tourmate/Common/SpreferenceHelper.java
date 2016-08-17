package com.ityadi.app.tourmate.Common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by taslim on 8/15/2016.
 */
public class SpreferenceHelper {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    public static final String USER = "username";

    public SpreferenceHelper(Context context) {
        sharedPreferences = context.getSharedPreferences("tourmate",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void save(String data) {
        editor.putString(USER,data); // to write
        editor.commit(); //to save
    }

    public String retrive() {
        return sharedPreferences.getString(USER,"");
    }

    public void clean() {
        editor.remove(USER);
        editor.apply();
    }
}