package ru.egmohf.cyberjur.ui.popup.modal;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import ru.egmohf.cyberjur.R;
import ru.egmohf.cyberjur.databinding.ModalBinding;

public class ModalDialog extends Dialog {
    Modal modal;
    private ModalBinding binding;

    public ModalDialog(@NonNull Context context) {
        super(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        this.getWindow().setBackgroundDrawableResource(R.color.bgTransparent50);
        this.modal = null;
        render();
    }

    public ModalDialog(@NonNull Context context, Modal modal) {
        super(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        this.getWindow().setBackgroundDrawableResource(R.color.bgTransparent50);
        this.modal = modal;
        render();
    }

    public void render() {
        binding = ModalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.modalClose.setOnClickListener(v -> dismiss());

        if (modal != null) {
            this.modal.setBinding(binding);
            this.modal.setContext(this.getContext());
            this.modal.setInflater(this.getLayoutInflater());
            binding.modalView.addView(modal.render());
            WindowManager windowManager = (WindowManager) this.getContext().getSystemService(Context.WINDOW_SERVICE);
            int screenWidth, screenHeight;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                WindowMetrics windowMetrics = windowManager.getCurrentWindowMetrics();
                Rect bounds = windowMetrics.getBounds();
                screenWidth = bounds.width();
                screenHeight = bounds.height();
            } else {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                windowManager.getDefaultDisplay().getMetrics(displayMetrics);
                screenWidth = displayMetrics.widthPixels;
                screenHeight = displayMetrics.heightPixels;
            }

            int targetWidth = (int) (screenWidth * modal.getWidthPercent());

            ViewGroup.LayoutParams layoutParams = binding.modalView.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.width = targetWidth == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : targetWidth;
                binding.modalView.setLayoutParams(layoutParams);
            }
        }

    }
}
