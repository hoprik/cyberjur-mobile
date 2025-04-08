package ru.egmohf.cyberjur.ui.widget.image;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;
import okhttp3.Response;
import ru.egmohf.cyberjur.R;
import ru.egmohf.cyberjur.api.helpers.ApiHelper;
import ru.egmohf.cyberjur.api.helpers.ResponseCallback;
import ru.egmohf.cyberjur.api.helpers.ResponseWrapper;
import ru.egmohf.cyberjur.saveData.Cache;

import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class CImageView extends AppCompatImageView {
    ImageDrawable drawable;
    private float[] rounds = new float[8];
    public CImageView(Context context) {
        super(context);
        drawable = new ImageDrawable(null, this);
    }

    public CImageView(Context context, AttributeSet attrs) {
        super(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseWidget);
        drawable = new ImageDrawable(typedArray, this);
        String image = typedArray.getString(R.styleable.BaseWidget_imageUrl);
        if (image != null && !image.isEmpty()){
            setImage(image);
        }
    }

    public void setImage(String url) {
        String[] keys = url.split("/");
        String address = keys[keys.length - 2]+"-"+keys[keys.length - 1].split("")[0];
        AtomicReference<Bitmap> cachedBitmap = new AtomicReference<>();
        Cache.getInstance().getBitmapFromDisk(address, bitmap -> {
            if (bitmap != null){
                drawable.setImage(bitmap);
                setBackground(drawable);
                cachedBitmap.set(bitmap);
            }
        });
        ApiHelper.downloadImage(url, new ResponseCallback() {
            @Override
            public void onSuccess(ResponseWrapper wrapper) {
                Response response = wrapper.response;
                InputStream inputStream = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                post(() -> {
                    drawable.setImage(bitmap);
                    setBackground(drawable);
                });
                if (cachedBitmap.get() == null){
                    Cache.getInstance().setData(address, bitmap);
                }
            }

            @Override
            public void onFailure(ResponseWrapper wrapper) {

            }
        });
    }

    public void setRound(int radius) {
        Arrays.fill(rounds, (float) radius);
        this.drawable.setCornerRadii(rounds);
    }

    public void setRoundX(int radius) {
        rounds[0] = (float) radius; // TLx
        rounds[2] = (float) radius; // TRx
        rounds[4] = (float) radius; // BRx
        rounds[6] = (float) radius; // BLx
        this.drawable.setCornerRadii(rounds);
    }

    public void setRoundY(int radius) {
        rounds[1] = (float) radius; // TLy
        rounds[3] = (float) radius; // TRy
        rounds[5] = (float) radius; // BRy
        rounds[7] = (float) radius; // BLy
        this.drawable.setCornerRadii(rounds);
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
        this.drawable.setCornerRadii(rounds);
    }
}
