package ru.egmohf.cyberjur.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import ru.egmohf.cyberjur.api.interfaces.User;

public class CTextView extends AppCompatTextView {
    public CTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        new BaseDrawable(this, attrs);
        if (this.getText().toString().contains("%name%")){
            this.setText(getText().toString().replace("%name%", User.getMyProfile().getInfo().getName()));
        }
        if (this.getText().toString().contains("%surname%")){
            this.setText(getText().toString().replace("%surname%", User.getMyProfile().getInfo().getSurname()));
        }
        if (this.getText().toString().contains("%money%")){
            this.setText(getText().toString().replace("%money%", "" + User.getMyProfile().getMoney()));
        }
        if (this.getText().toString().contains("%like%")){
            this.setText(getText().toString().replace("%like%", "" + User.getMyProfile().getThanks()));
        }
    }
}
