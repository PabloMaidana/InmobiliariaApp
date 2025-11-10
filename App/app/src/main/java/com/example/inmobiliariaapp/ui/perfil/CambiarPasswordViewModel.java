package com.example.inmobiliariaapp.ui.perfil;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariaapp.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambiarPasswordViewModel extends AndroidViewModel {

    private MutableLiveData<String> mensaje = new MutableLiveData<>();

    public CambiarPasswordViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMensaje() {
        return mensaje;
    }

    public void cambiarPassword(String actual, String nueva) {
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();

        Call<Void> llamada = api.cambiarPassword("Bearer " + token, actual, nueva);
        llamada.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    mensaje.postValue("Contraseña actualizada correctamente");
                } else {
                    mensaje.postValue("Error: la contraseña actual es incorrecta");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mensaje.postValue("Error de servidor");
            }
        });
    }
}