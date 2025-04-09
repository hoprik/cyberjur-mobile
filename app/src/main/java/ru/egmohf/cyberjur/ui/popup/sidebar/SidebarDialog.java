package ru.egmohf.cyberjur.ui.popup.sidebar;

import android.app.Dialog;
import android.content.Context;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import androidx.annotation.NonNull;
import ru.egmohf.cyberjur.R;
import ru.egmohf.cyberjur.databinding.SidebarBinding;

public class SidebarDialog extends Dialog {
    private float x1;
    static final int MIN_DISTANCE = 250;
    private SidebarBinding binding;
    private final Sidebar sidebar;

    public SidebarDialog(Context context) {
        super(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        this.getWindow().setWindowAnimations(R.style.DialogSlideAnimation);
        this.sidebar = null;
        render();
    }

    public SidebarDialog(@NonNull Context context, Sidebar sidebar) {
        super(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        this.getWindow().setWindowAnimations(R.style.DialogSlideAnimation);
        this.sidebar = sidebar;
        render();
    }

    public void render(){
        binding = SidebarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.sidebarClose.setOnClickListener(v -> dismiss());

        if (this.sidebar != null) {
            if (sidebar instanceof ISidebarMenuItems){
                binding.sidebarContextMenu.setOnClickListener(v->{
                    ISidebarMenuItems sidebarMenuItems = (ISidebarMenuItems) sidebar;
                    PopupMenu popupMenu = new PopupMenu(getContext(), binding.sidebarContextMenu);
                    sidebarMenuItems.getMenuItems().forEach(item -> popupMenu.getMenu().add(item));
                    popupMenu.setOnMenuItemClickListener(sidebarMenuItems.getOnMenuItemClickListener());
                    popupMenu.show();
                });
            }else{
                binding.sidebarContextMenu.setVisibility(View.GONE);
            }

            this.sidebar.setBinding(binding);
            this.sidebar.setContext(this.getContext());
            this.sidebar.setInflater(this.getLayoutInflater());
            binding.view.addView(sidebar.render());
        }
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                float x2 = event.getX();
                float deltaX = x2 - x1;
                if (deltaX > MIN_DISTANCE) {
                    this.dismiss();
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
