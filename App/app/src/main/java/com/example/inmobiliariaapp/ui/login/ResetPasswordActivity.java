package com.example.inmobiliariaapp.ui.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliariaapp.R;
import com.example.inmobiliariaapp.databinding.ActivityResetPasswordBinding;

public class ResetPasswordActivity extends AppCompatActivity {

    private ActivityResetPasswordBinding binding;
    private ResetPasswordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ResetPasswordViewModel.class);

        binding.btnEnviar.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString();
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Ingrese su correo electrónico", Toast.LENGTH_SHORT).show();
                return;
            }
            viewModel.resetearPassword(email);
        });

        viewModel.getMensaje().observe(this, msg ->
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        );
    }
}