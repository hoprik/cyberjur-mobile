package ru.egmohf.cyberjur.ui.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ru.egmohf.cyberjur.R;
import ru.egmohf.cyberjur.databinding.ActivityLoginBinding;
import ru.egmohf.cyberjur.ui.PopupEngine;
import ru.egmohf.cyberjur.ui.widget.CConstraintLayout;
import ru.egmohf.cyberjur.ui.widget.Input;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PopupEngine.getINSTANCE().changeActivity(this);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        CConstraintLayout root = binding.getRoot();
        setContentView(root);

        root.findViewById(R.id.loginButton).setOnClickListener(view -> {
            Input loginOrEmail = root.findViewById(R.id.editTextText);
            Input password = root.findViewById(R.id.editTextText2);
        });
    }
}
