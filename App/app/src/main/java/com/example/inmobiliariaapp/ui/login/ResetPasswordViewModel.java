package com.example.inmobiliariaapp.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariaapp.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordViewModel extends AndroidViewModel {

    private MutableLiveData<String> mensaje = new MutableLiveData<>();

    public ResetPasswordViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMensaje() {
        return mensaje;
    }

    public void resetearPassword(String email) {
        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();

        Call<String> llamada = api.resetearPassword(email);
        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    mensaje.postValue("Correo de restablecimiento enviado correctamente");
                } else {
                    mensaje.postValue("No se encontró un usuario con ese email");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mensaje.postValue("Error de conexión con el servidor");
            }
        });
    }
}
