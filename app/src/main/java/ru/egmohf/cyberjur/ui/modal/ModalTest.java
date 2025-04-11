package ru.egmohf.cyberjur.ui.modal;

import android.view.View;
import ru.egmohf.cyberjur.databinding.TestModalBinding;
import ru.egmohf.cyberjur.ui.popup.modal.Modal;

public class ModalTest extends Modal {
    @Override
    public View render() {
        TestModalBinding binding = TestModalBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
