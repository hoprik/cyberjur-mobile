package ru.egmohf.cyberjur.ui.widget.helpers;

import android.util.Log;

import java.util.Arrays;

public class DrawableHelper implements IBaseDrawable{
    private String bgColor = "#000000";
    private float[] rounds = new float[8]; // [TLx, TLy, TRx, TRy, BRx, BRy, BLx, BLy]
    public BaseDrawable drawable;
    private float borderWidth = 0;
    private String borderColor = "#000000";
    private float borderDashWidth = 0;
    private float borderDashGap = 0;

    public DrawableHelper(BaseDrawable drawable) {
        this.drawable = drawable;
    }

    public void setBgColor(String color) {
        this.bgColor = color;
        this.drawable.aplayTheme();
    }

    public String getBgColor() {
        return this.bgColor;
    }

    public void setRound(int radius) {
        Arrays.fill(rounds, (float) radius);
        this.drawable.aplayTheme();
    }

    public void setRoundX(int radius) {
        rounds[0] = (float) radius; // TLx
        rounds[2] = (float) radius; // TRx
        rounds[4] = (float) radius; // BRx
        rounds[6] = (float) radius; // BLx
        this.drawable.aplayTheme();
    }

    public void setRoundY(int radius) {
        rounds[1] = (float) radius; // TLy
        rounds[3] = (float) radius; // TRy
        rounds[5] = (float) radius; // BRy
        rounds[7] = (float) radius; // BLy
        this.drawable.aplayTheme();
    }

    public void setRound(int topLeft, int topRight, int bottomRight, int bottomLeft) {
        rounds[0] = topLeft; // TLx
        rounds[1] = topLeft; // TLy
        rounds[2] = topRight; // TRx
        rounds[3] = topRight; // TRy
        rounds[4] = bottomRight; // BRx
        rounds[5] = bottomRight; // BRy
        rounds[6] = bottomLeft; // BLx
        rounds[7] = bottomLeft; // BLy
        this.drawable.aplayTheme();
    }

    public float[] getRound() {
        return this.rounds;
    }

    @Override
    public void setBorderWidth(float width) {
        this.borderWidth = width;
        this.drawable.aplayTheme();
    }

    @Override
    public float getBorderWidth() {
        return this.borderWidth;
    }

    @Override
    public void setBorderColor(String color) {
        this.borderColor = color;
        this.drawable.aplayTheme();
    }

    @Override
    public String getBorderColor() {
        return this.borderColor;
    }

    @Override
    public void setBorderDashWidth(float width) {
        this.borderDashWidth = width;
        this.drawable.aplayTheme();
    }

    @Override
    public float getBorderDashWidth() {
        return this.borderDashWidth;
    }

    @Override
    public void setBorderDashGap(float gap) {
        this.borderDashGap = gap;
    }

    @Override
    public float getBorderDashGap() {
        return this.borderDashGap;
    }
}