package ru.egmohf.cyberjur.ui.popup.sidebar;

import android.widget.PopupMenu;

import java.util.List;

public interface ISidebarMenuItems {
    List<String> getMenuItems();
    PopupMenu.OnMenuItemClickListener getOnMenuItemClickListener();
}
