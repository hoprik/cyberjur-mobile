package ru.egmohf.cyberjur.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import ru.egmohf.cyberjur.ui.widget.helpers.BaseDrawable;
import ru.egmohf.cyberjur.ui.widget.helpers.DrawableHelper;
import ru.egmohf.cyberjur.ui.widget.helpers.IBaseDrawable;

public class CView extends View implements IBaseDrawable {
    private DrawableHelper helper;
    public CView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        BaseDrawable drawable = new BaseDrawable(this, attrs);
        this.helper = new DrawableHelper(drawable);
    }

    public CView(Context context) {
        super(context);
        BaseDrawable drawable = new BaseDrawable(this, null);
        this.helper = new DrawableHelper(drawable);
        this.helper.drawable.aplayTheme();
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

    @Override
    public void setBorderWidth(float width) {
        this.helper.setBorderWidth(width);
    }

    @Override
    public float getBorderWidth() {
        return this.helper.getBorderWidth();
    }

    @Override
    public void setBorderColor(String color) {
        this.helper.setBorderColor(color);
    }

    @Override
    public String getBorderColor() {
        return this.helper.getBorderColor();
    }

    @Override
    public void setBorderDashWidth(float width) {
        this.helper.setBorderDashWidth(width);
    }

    @Override
    public float getBorderDashWidth() {
        return this.helper.getBorderDashWidth();
    }

    @Override
    public void setBorderDashGap(float gap) {
        this.helper.setBorderDashGap(gap);
    }

    @Override
    public float getBorderDashGap() {
        return this.helper.getBorderDashGap();
    }
}
