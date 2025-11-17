package com.example.inmobiliariaapp;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariaapp.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> mLoginResultado = new MutableLiveData<>();
    private MutableLiveData<String> mMensaje = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Boolean> getMLoginResultado() {
        return mLoginResultado;
    }

    public LiveData<String> getMMensaje() {
        return mMensaje;
    }

    public void login(String usuario, String password) {


        if (usuario == null || usuario.isEmpty()) {
            mMensaje.postValue("Debe ingresar un usuario");
            return;
        }
        if (password == null || password.isEmpty()) {
            mMensaje.postValue("Debe ingresar una contraseña");
            return;
        }

        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        Call<String> llamada = api.login(usuario, password);

        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {

                    String token = response.body();
                    ApiClient.guardarToken(getApplication(), token);

                    mLoginResultado.postValue(true);

                } else {
                    mMensaje.postValue("Usuario o contraseña incorrectos");
                    mLoginResultado.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mMensaje.postValue("Error de conexión: " + t.getMessage());
                mLoginResultado.postValue(false);
            }
        });
    }
}
