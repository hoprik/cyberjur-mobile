package ru.egmohf.cyberjur.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class CTextView extends AppCompatTextView {
    public CTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        new BaseDrawable(this, attrs);
    }
}
