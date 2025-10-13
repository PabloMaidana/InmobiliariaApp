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
    private MutableLiveData<Boolean> mLoginResultado  = new MutableLiveData<>();
    private String usuario;
    private String password;
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<Boolean> getMLoginResultado(){
        return mLoginResultado;
    }
    public void login(String usuario, String password){
        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        Call<String> llamada = api.login(usuario,password);
        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    String token = response.body();
                    ApiClient.guardarToken(getApplication(), token);
                    // mMensaje.postValue("Bienvenido");
                    // postvalue al ser una funcion asincrona

                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplication().startActivity(intent);
                }else{
                    // mMensaje.postValue("Usuario y/o contraseñas incorrectas");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //mMensaje.postValue("Error de servidor");
            }
        });

    }
}
