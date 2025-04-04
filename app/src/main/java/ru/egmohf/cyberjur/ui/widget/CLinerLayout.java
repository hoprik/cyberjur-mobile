package ru.egmohf.cyberjur.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;

public class CLinerLayout extends LinearLayout {
    public CLinerLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        new BaseDrawable(this, attrs);
    }
}
