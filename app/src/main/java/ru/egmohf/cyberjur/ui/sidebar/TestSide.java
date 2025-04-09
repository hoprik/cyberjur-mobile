package ru.egmohf.cyberjur.ui.sidebar;

import android.util.Log;
import android.view.View;
import android.widget.PopupMenu;
import ru.egmohf.cyberjur.databinding.TestSideBinding;
import ru.egmohf.cyberjur.ui.popup.sidebar.ISidebarMenuItems;
import ru.egmohf.cyberjur.ui.popup.sidebar.Sidebar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestSide extends Sidebar {
    @Override
    public View render() {
        TestSideBinding binding = TestSideBinding.inflate(this.getLayoutInflater());
        return binding.getRoot();
    }
}
