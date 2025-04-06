package ru.egmohf.cyberjur.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import org.jetbrains.annotations.NotNull;
import ru.egmohf.cyberjur.R;
import ru.egmohf.cyberjur.saveData.Cache;
import ru.egmohf.cyberjur.ui.widget.helpers.BaseDrawable;
import ru.egmohf.cyberjur.ui.widget.helpers.DrawableHelper;
import ru.egmohf.cyberjur.ui.widget.helpers.IBaseDrawable;

public class CButton extends AppCompatButton implements IBaseDrawable {
    private DrawableHelper helper;

    public CButton(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        BaseDrawable drawable = new BaseDrawable(this, attrs);
        this.helper = new DrawableHelper(drawable);

        if (attrs != null) {
            TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.BaseWidget);
            setAttributes(attributes);
        }
    }

    public CButton(Context context) {
        super(context);
        BaseDrawable drawable = new BaseDrawable(this, null);
        this.helper = new DrawableHelper(drawable);
        this.helper.drawable.aplayTheme();
    }

    private void setAttributes(TypedArray a) {
        String textColor = a.getString(R.styleable.BaseWidget_txtColor);
        setTextColor(textColor);
        String textHintColor = a.getString(R.styleable.BaseWidget_txtHintColor);
        setHintTextColor(textHintColor);
    }

    public void setTextColor(String textColor) {
        if (textColor == null) textColor = "#ffffff";
        if (Cache.getInstance() != null && Cache.getInstance().hasData(textColor))
            textColor = Cache.getInstance().getStringData(textColor, "");
        setTextColor(Color.parseColor(textColor));
    }

    public void setHintTextColor(String textHintColor) {
        if (textHintColor == null) textHintColor = "#80ffffff";
        if (Cache.getInstance() != null && Cache.getInstance().hasData(textHintColor))
            textHintColor = Cache.getInstance().getStringData(textHintColor, "");
        setHintTextColor(Color.parseColor(textHintColor));
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
