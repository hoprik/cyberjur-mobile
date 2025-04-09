package ru.egmohf.cyberjur.ui.sidebar;

import android.view.View;
import ru.egmohf.cyberjur.databinding.TestSideBinding;
import ru.egmohf.cyberjur.ui.popup.sidebar.Sidebar;

public class TestSide extends Sidebar {
    @Override
    public View render() {
        TestSideBinding binding = TestSideBinding.inflate(this.getLayoutInflater());
        return binding.getRoot();
    }
}
