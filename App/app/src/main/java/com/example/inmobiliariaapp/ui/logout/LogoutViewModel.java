package com.example.inmobiliariaapp.ui.logout;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliariaapp.LoginActivity;
import com.example.inmobiliariaapp.request.ApiClient;

public class LogoutViewModel extends AndroidViewModel {

    public LogoutViewModel(@NonNull Application application) {
        super(application);
    }

    public void cerrarSesion() {

        ApiClient.guardarToken(getApplication(), "");

        Toast.makeText(getApplication(), "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show();


        Intent intent = new Intent(getApplication(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getApplication().startActivity(intent);
    }
}