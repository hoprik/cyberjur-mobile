package ru.egmohf.cyberjur.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatEditText;

public class Input extends AppCompatEditText {
    public Input(Context context, AttributeSet attrs) {
        super(context, attrs);
        new BaseDrawable(this, attrs);
    }
}
