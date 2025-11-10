package com.example.inmobiliariaapp.ui.contratos;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliariaapp.model.Inmueble;
import com.example.inmobiliariaapp.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratosViewModel extends AndroidViewModel {
    private MutableLiveData<List<Inmueble>> inmueblesConContrato = new MutableLiveData<>();

    public ContratosViewModel(@NonNull Application application) {
        super(application);
        cargarInmueblesConContrato();
    }

    public LiveData<List<Inmueble>> getInmueblesConContrato() {
        return inmueblesConContrato;
    }

    private void cargarInmueblesConContrato() {
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();

        Call<List<Inmueble>> llamada = api.obtenerInmueblesConContratoVigente("Bearer " + token);
        llamada.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()) {
                    inmueblesConContrato.postValue(response.body());
                } else {
                    Toast.makeText(getApplication(), "No hay contratos vigentes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                Toast.makeText(getApplication(), "Error de servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}