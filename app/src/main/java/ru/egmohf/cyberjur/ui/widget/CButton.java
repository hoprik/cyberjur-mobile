package ru.egmohf.cyberjur.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

public class CButton extends AppCompatButton {
    public CButton(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setTransformationMethod(null);
        new BaseDrawable(this, attrs);
    }
}
