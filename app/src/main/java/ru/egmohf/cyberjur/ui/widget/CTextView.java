package ru.egmohf.cyberjur.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import ru.egmohf.cyberjur.R;
import ru.egmohf.cyberjur.api.interfaces.User;
import ru.egmohf.cyberjur.saveData.Cache;
import ru.egmohf.cyberjur.ui.widget.helpers.BaseDrawable;
import ru.egmohf.cyberjur.ui.widget.helpers.DrawableHelper;
import ru.egmohf.cyberjur.ui.widget.helpers.IBaseDrawable;

public class CTextView extends AppCompatTextView implements IBaseDrawable {
    private DrawableHelper helper;
    public CTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        if (attrs != null){
            TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.BaseWidget);
            setAttributes(attributes);
        }

        BaseDrawable drawable = new BaseDrawable(this, attrs);
        this.helper = new DrawableHelper(drawable);

        if (this.getText().toString().contains("%name%")) {
            this.setText(getText().toString().replace("%name%", User.getMyProfile().getInfo().getName()));
        }
        if (this.getText().toString().contains("%surname%")) {
            this.setText(getText().toString().replace("%surname%", User.getMyProfile().getInfo().getSurname()));
        }
        if (this.getText().toString().contains("%money%")) {
            this.setText(getText().toString().replace("%money%", "" + User.getMyProfile().getMoney()));
        }
        if (this.getText().toString().contains("%like%")) {
            this.setText(getText().toString().replace("%like%", "" + User.getMyProfile().getThanks()));
        }
    }

    public CTextView(Context context) {
        super(context);
        BaseDrawable drawable = new BaseDrawable(this, null);
        this.helper = new DrawableHelper(drawable);
        this.helper.drawable.aplayTheme();

        if (this.getText().toString().contains("%name%")) {
            this.setText(getText().toString().replace("%name%", User.getMyProfile().getInfo().getName()));
        }
        if (this.getText().toString().contains("%surname%")) {
            this.setText(getText().toString().replace("%surname%", User.getMyProfile().getInfo().getSurname()));
        }
        if (this.getText().toString().contains("%money%")) {
            this.setText(getText().toString().replace("%money%", "" + User.getMyProfile().getMoney()));
        }
        if (this.getText().toString().contains("%like%")) {
            this.setText(getText().toString().replace("%like%", "" + User.getMyProfile().getThanks()));
        }
    }

    private void setAttributes(TypedArray a) {
        String textColor = a.getString(R.styleable.BaseWidget_txtColor);
        setTextColor(textColor);
        String textHintColor = a.getString(R.styleable.BaseWidget_txtHintColor);
        setHintTextColor(textHintColor);
        a.recycle();
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
