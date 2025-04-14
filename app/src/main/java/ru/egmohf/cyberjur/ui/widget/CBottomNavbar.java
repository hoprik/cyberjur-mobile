package ru.egmohf.cyberjur.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.jetbrains.annotations.NotNull;

public class CBottomNavbar extends BottomNavigationView {
    public CBottomNavbar(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getMaxItemCount() {
        return 10;
    }

    @Override
    public int getLabelVisibilityMode() {
        return LABEL_VISIBILITY_UNLABELED;
    }
}
