package ru.egmohf.cyberjur.ui.popup.modal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import ru.egmohf.cyberjur.databinding.ModalBinding;

public abstract class Modal {
    private Context context;
    private LayoutInflater inflater;
    private ModalBinding binding;
    private float widthPercent = 0;

    public Modal() {
    }

    public Context getContext() {
        return context;
    }

    public LayoutInflater getLayoutInflater() {
        return inflater;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public ModalBinding getBinding() {
        return binding;
    }

    public void setBinding(ModalBinding binding) {
        this.binding = binding;
    }

    public void setWidthPercent(float percent) {
        this.widthPercent = percent;
    }

    public float getWidthPercent() {
        return widthPercent;
    }

    public float heightPercent(float percent) {
        return context.getResources().getDisplayMetrics().heightPixels * percent / 100;
    }

    public abstract View render();
}
