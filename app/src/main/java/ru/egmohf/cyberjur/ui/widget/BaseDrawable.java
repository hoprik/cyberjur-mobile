package ru.egmohf.cyberjur.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import org.w3c.dom.Text;
import ru.egmohf.cyberjur.R;
import ru.egmohf.cyberjur.saveData.Cache;
import ru.egmohf.cyberjur.saveData.Cache;

import java.lang.reflect.Method;

public class BaseDrawable extends GradientDrawable {
    public BaseDrawable(View view, AttributeSet attrs) {
        super();
        Context context = view.getContext();
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.BaseWidget);

        // Set corner radii
        float[] radii = getCornerRadius(attributes);
        setCornerRadii(radii);

        // Set background color
        String bgColor = attributes.getString(R.styleable.BaseWidget_bgColor);
        if (bgColor == null) bgColor = "#00000000";
        if (Cache.getInstance() != null &&Cache.getInstance().hasData(bgColor)) bgColor = Cache.getInstance().getStringData(bgColor, "");
        setColor(Color.parseColor(bgColor));

        if (view instanceof TextView) {
            String textColor = attributes.getString(R.styleable.BaseWidget_txtColor);
            if (textColor == null) textColor = "#ffffff";
            if (Cache.getInstance() != null && Cache.getInstance().hasData(textColor)) textColor = Cache.getInstance().getStringData(textColor, "");
            ((TextView)view).setTextColor(Color.parseColor(textColor));

            String textHintColor = attributes.getString(R.styleable.BaseWidget_txtHintColor);
            if (textHintColor == null) textHintColor = "#80ffffff";
            if (Cache.getInstance() != null && Cache.getInstance().hasData(textColor)) textHintColor = Cache.getInstance().getStringData(textHintColor, "");
            ((TextView)view).setHintTextColor(Color.parseColor(textHintColor));
        }

        attributes.recycle();
        view.setBackground(this);
    }

    private float[] getCornerRadius(TypedArray attributes) {
        float[] radii = new float[8]; // 8 values for [TLx, TLy, TRx, TRy, BRx, BRy, BLx, BLy]

        // Default all corners
        if (attributes.hasValue(R.styleable.BaseWidget_round)) {
            float radius = attributes.getDimension(R.styleable.BaseWidget_round, 0);
            for (int i = 0; i < radii.length; i++) {
                radii[i] = radius;
            }
        }

        // Horizontal radii (X-axis)
        if (attributes.hasValue(R.styleable.BaseWidget_roundX)) {
            float radiusX = attributes.getDimension(R.styleable.BaseWidget_roundX, 0);
            radii[0] = radiusX; // TLx
            radii[2] = radiusX; // TRx
            radii[4] = radiusX; // BRx
            radii[6] = radiusX; // BLx
        }

        // Vertical radii (Y-axis)
        if (attributes.hasValue(R.styleable.BaseWidget_roundY)) {
            float radiusY = attributes.getDimension(R.styleable.BaseWidget_roundY, 0);
            radii[1] = radiusY; // TLy
            radii[3] = radiusY; // TRy
            radii[5] = radiusY; // BRy
            radii[7] = radiusY; // BLy
        }

        // Individual corners (override general settings)
        setCornerValue(attributes, R.styleable.BaseWidget_roundLeft, radii, 0, 1);
        setCornerValue(attributes, R.styleable.BaseWidget_roundTop, radii, 2, 3);
        setCornerValue(attributes, R.styleable.BaseWidget_roundRight, radii, 4, 5);
        setCornerValue(attributes, R.styleable.BaseWidget_roundBottom, radii, 6, 7);

        return radii;
    }

    private void setCornerValue(TypedArray attributes, int styleableId, float[] radii, int xIndex, int yIndex) {
        if (attributes.hasValue(styleableId)) {
            float radius = attributes.getDimension(styleableId, 0);
            radii[xIndex] = radius;
            radii[yIndex] = radius;
        }
    }

    @Override
    public void setAlpha(int alpha) {
        super.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        super.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}