package ru.egmohf.cyberjur.ui.popup.sidebar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import ru.egmohf.cyberjur.databinding.SidebarBinding;

public abstract class Sidebar {
    private Context context;
    private LayoutInflater inflater;
    private SidebarBinding binding;

    public Sidebar() {
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

    public SidebarBinding getBinding() {
        return binding;
    }

    public void setBinding(SidebarBinding binding) {
        this.binding = binding;
    }

    public abstract View render();
}
