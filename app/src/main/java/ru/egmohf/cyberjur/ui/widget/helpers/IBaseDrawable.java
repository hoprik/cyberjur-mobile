package ru.egmohf.cyberjur.ui.widget.helpers;

public interface IBaseDrawable {
    void setBgColor(String color);
    String getBgColor();
    void setRound(int radius);
    void setRoundX(int radius);
    void setRoundY(int radius);
    void setRound(int topLeft, int topRight, int bottomRight, int bottomLeft);
    float[] getRound();
}
