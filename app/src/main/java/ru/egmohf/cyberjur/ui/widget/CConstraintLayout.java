package ru.egmohf.cyberjur.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import org.jetbrains.annotations.NotNull;

public class CConstraintLayout extends ConstraintLayout {
    public CConstraintLayout(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        new BaseDrawable(this, attrs);
    }
}
