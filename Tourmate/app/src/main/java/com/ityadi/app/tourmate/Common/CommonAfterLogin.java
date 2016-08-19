package com.ityadi.app.tourmate.Common;

import android.content.Context;
import android.content.Intent;

import com.ityadi.app.tourmate.Activity.SigninSignup;

/**
 * Created by taslim on 8/18/2016.
 */
public class CommonAfterLogin {
    Context context;
    SpreferenceHelper spreferenceHelper;

    public void signout(Context context) {

        spreferenceHelper = new SpreferenceHelper(context);
        spreferenceHelper.clean();

        Intent i = new Intent(context, SigninSignup.class);
        context.startActivity(i);
    }


}
