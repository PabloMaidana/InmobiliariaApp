package com.example.inmobiliariaapp.ui.perfil;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.inmobiliariaapp.databinding.FragmentCambiarPasswordBinding;

public class CambiarPasswordFragment extends Fragment {

    private FragmentCambiarPasswordBinding binding;
    private CambiarPasswordViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCambiarPasswordBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(CambiarPasswordViewModel.class);

        binding.btnGuardarCambios.setOnClickListener(v -> {
            String actual = binding.etPasswordActual.getText().toString();
            String nueva = binding.etPasswordNueva.getText().toString();
            String repetir = binding.etRepetirPassword.getText().toString();

            if (TextUtils.isEmpty(actual) || TextUtils.isEmpty(nueva) || TextUtils.isEmpty(repetir)) {
                Toast.makeText(getContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!nueva.equals(repetir)) {
                Toast.makeText(getContext(), "Las contraseñas nuevas no coinciden", Toast.LENGTH_SHORT).show();
                return;
            }

            viewModel.cambiarPassword(actual, nueva);
        });

        viewModel.getMensaje().observe(getViewLifecycleOwner(), msg ->
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show()
        );

        return binding.getRoot();
    }
}