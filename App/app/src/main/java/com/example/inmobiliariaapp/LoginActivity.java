package com.example.inmobiliariaapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliariaapp.databinding.ActivityLoginBinding;
import com.example.inmobiliariaapp.databinding.ActivityMainBinding;
import com.example.inmobiliariaapp.ui.login.ResetPasswordActivity;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel vm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        vm = new ViewModelProvider(this).get(LoginViewModel.class);
        setContentView(binding.getRoot());

        vm.getMMensaje().observe(this, mensaje -> {
            if (mensaje != null) {
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
            }
        });

        vm.getMLoginResultado().observe(this, exito -> {
            if (exito != null && exito) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.btnLogin.setOnClickListener(onClickListener -> {
            String usuario = binding.etUsuario.getText().toString();
            String password = binding.etPassword.getText().toString();
            vm.login(usuario, password);
        });

        TextView tvSignup = findViewById(R.id.tvSignup);
        tvSignup.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
            startActivity(intent);
        });
    }
}