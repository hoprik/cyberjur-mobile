package ru.egmohf.cyberjur.ui.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import ru.egmohf.cyberjur.api.interfaces.User;
import ru.egmohf.cyberjur.databinding.FragmentHomeBinding;
import ru.egmohf.cyberjur.ui.PopupEngine;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        User.getOne();

        binding.notifyButton.setOnClickListener(view -> {
            PopupEngine.getINSTANCE().sendNotify("Тест");
        });

        binding.notifyButtonRed.setOnClickListener(view -> {
            PopupEngine.getINSTANCE().sendNotify("Тест c другим цветов", "red");
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}