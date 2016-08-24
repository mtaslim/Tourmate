package com.ityadi.app.tourmate.Common;

import android.graphics.Color;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by taslim on 8/23/2016.
 */
public class DisableUi {
    public void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }
    public void disableTextView(TextView textView) {
        textView.setFocusable(false);
        textView.setEnabled(false);
        textView.setCursorVisible(false);
        textView.setKeyListener(null);
        textView.setBackgroundColor(Color.TRANSPARENT);
    }
    public void anableEditText(EditText editText) {
        editText.setFocusable(true);
        editText.setEnabled(true);
        editText.setCursorVisible(true);
        editText.setBackgroundColor(Color.BLACK);
    }
    public void anableTextView(TextView textView) {
        textView.setFocusable(true);
        textView.setEnabled(true);
        textView.setCursorVisible(true);
        textView.setBackgroundColor(Color.BLACK);
    }


}
