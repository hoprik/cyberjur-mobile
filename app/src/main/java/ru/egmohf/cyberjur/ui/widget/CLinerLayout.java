package ru.egmohf.cyberjur.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import ru.egmohf.cyberjur.ui.widget.helpers.BaseDrawable;
import ru.egmohf.cyberjur.ui.widget.helpers.DrawableHelper;
import ru.egmohf.cyberjur.ui.widget.helpers.IBaseDrawable;

public class CLinerLayout extends LinearLayout implements IBaseDrawable {
    private DrawableHelper helper;
    public CLinerLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        BaseDrawable drawable = new BaseDrawable(this, attrs);
        this.helper = new DrawableHelper(drawable);
    }

    public CLinerLayout(Context context) {
        super(context);
        BaseDrawable drawable = new BaseDrawable(this, null);
        this.helper = new DrawableHelper(drawable);
    }

    @Override
    public void setBgColor(String color) {
        this.helper.setBgColor(color);
    }

    @Override
    public String getBgColor() {
        return this.helper.getBgColor();
    }

    @Override
    public void setRound(int round) {
        this.helper.setRound(round);
    }

    @Override
    public void setRoundX(int round) {
        this.helper.setRoundX(round);
    }

    @Override
    public void setRoundY(int round) {
        this.helper.setRoundY(round);
    }

    @Override
    public void setRound(int topLeft, int topRight, int bottomRight, int bottomLeft) {
        this.helper.setRound(topLeft, topRight, bottomRight, bottomLeft);
    }

    @Override
    public float[] getRound() {
        return helper.getRound();
    }
}
