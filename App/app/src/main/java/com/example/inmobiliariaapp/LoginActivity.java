package com.example.inmobiliariaapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliariaapp.databinding.ActivityLoginBinding;
import com.example.inmobiliariaapp.databinding.ActivityMainBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel vm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        vm = new ViewModelProvider(this).get(LoginViewModel.class);
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(onClickListener -> {
            String usuario = binding.etUsuario.getText().toString();
            String password = binding.etPassword.getText().toString();
            vm.login(usuario, password);
        });
    }
}