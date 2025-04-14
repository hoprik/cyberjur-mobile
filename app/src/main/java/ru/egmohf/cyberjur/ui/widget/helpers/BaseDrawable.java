package ru.egmohf.cyberjur.ui.widget.helpers;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import ru.egmohf.cyberjur.R;
import ru.egmohf.cyberjur.saveData.Cache;

public class BaseDrawable extends GradientDrawable {
    private View view;
    public BaseDrawable(View view, AttributeSet attrs) {
        super();
        this.view = view;;

        if (attrs != null){
            Context context = view.getContext();
            TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.BaseWidget);
            setAttributes(attributes);
        }
    }

    private void setAttributes(TypedArray attrs) {
        float[] radii = getCornerRadius(attrs);
        setCornerRadii(radii);

        // Set background color
        String bgColor = attrs.getString(R.styleable.BaseWidget_bgColor);
        if (bgColor == null) bgColor = "#00000000";
        if (Cache.getInstance().hasData(bgColor))
            bgColor = Cache.getInstance().getStringData(bgColor, "");
        setColor(Color.parseColor(bgColor));

        // Border settings
        float borderWidth = attrs.getDimension(R.styleable.BaseWidget_borderWidth, 0);
        if (borderWidth > 0) {
            String borderColor = attrs.getString(R.styleable.BaseWidget_borderColor);
            if (borderColor == null) borderColor = "#00000000";
            if (Cache.getInstance().hasData(borderColor))
                borderColor = Cache.getInstance().getStringData(borderColor, "");

            float dashWidth = attrs.getDimension(R.styleable.BaseWidget_borderDashWidth, 0);
            float dashGap = attrs.getDimension(R.styleable.BaseWidget_borderDashGap, 0);

            setStroke(
                    (int) borderWidth,
                    Color.parseColor(borderColor),
                    dashWidth,
                    dashGap
            );
        }

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

    public void aplayTheme() {
        if (view instanceof IBaseDrawable) {
            IBaseDrawable drawable = (IBaseDrawable) view;

            // Обновляем границы
            if (drawable.getBorderWidth() > 0) {
                setStroke(
                        (int) drawable.getBorderWidth(),
                        Color.parseColor(drawable.getBorderColor()),
                        drawable.getBorderDashWidth(),
                        drawable.getBorderDashGap()
                );
            }

            String color = drawable.getBgColor();
            if (color == null) color = "#00000000";
            if (Cache.getInstance().hasData(color))
                color = Cache.getInstance().getStringData(color, "");
            setColor(Color.parseColor(color));

            setCornerRadii(drawable.getRound());
        }
        view.setBackground(this);
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