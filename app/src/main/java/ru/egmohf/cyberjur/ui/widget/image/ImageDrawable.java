package ru.egmohf.cyberjur.ui.widget.image;

import android.content.res.TypedArray;
import android.graphics.*;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ru.egmohf.cyberjur.R;
import ru.egmohf.cyberjur.saveData.Cache;
import ru.egmohf.cyberjur.saveData.ConfigKeys;

import java.util.Arrays;

public class ImageDrawable extends GradientDrawable {
    private Bitmap bitmap;
    private float[] cornerRadius;

    public ImageDrawable(TypedArray attributes, View view){
        Log.d("ImageDrawable", String.valueOf(attributes != null));
        if (attributes != null) {
            float[] radii = getCornerRadius(attributes);
            this.cornerRadius = radii;
            setCornerRadii(radii);
        }
    }

    public void setImage(Bitmap bitmap){
        this.bitmap = bitmap;
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
    public void draw(@NonNull Canvas canvas) {
        RectF dstRectF = new RectF(getBounds());
        final Path path = new Path();
        path.addRoundRect(dstRectF, this.cornerRadius, Path.Direction.CW);
        canvas.clipPath(path);
        if(bitmap==null){
            Paint paint = new Paint();
            paint.setColor(Color.parseColor(Cache.getInstance().getStringData(ConfigKeys.BLUE.getKey(), "#000000")));
            canvas.drawRect(this.getBounds(), paint);
            return;
        }
        Rect src = new Rect(0, 0, this.bitmap.getWidth(), this.bitmap.getHeight());
        Rect dst = new Rect(this.getBounds());
        canvas.drawBitmap(this.bitmap,src, dst, new Paint());
    }

    @Override
    public void setAlpha(int i) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}
