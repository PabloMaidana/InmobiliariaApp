package com.example.inmobiliariaapp.ui.perfil;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariaapp.model.Propietario;
import com.example.inmobiliariaapp.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PerfilViewModel extends AndroidViewModel {
    private MutableLiveData<Propietario> mPropietario = new MutableLiveData<>();
    private MutableLiveData<Boolean> mEstado = new MutableLiveData<>();
    private MutableLiveData<String> mMensaje = new MutableLiveData<>();

    public PerfilViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Propietario> getMPropietario() {
        return mPropietario;
    }
    public LiveData<Boolean> getMEstado() {
        return mEstado;
    }
    public LiveData<String> getMMensaje() {
        return mMensaje;
    }

    public void guardar(String boton, String nombre, String apellido, String dni, String telefono, String email){
        if (boton.equalsIgnoreCase("editar")){
            mEstado.postValue(true);
            mMensaje.postValue("Guardar");
        }else{
            //  Validar campos
            Propietario propietario = new Propietario();
            propietario.setIdPropietario(mPropietario.getValue().getIdPropietario());

            propietario.setNombre(nombre);
            propietario.setApellido(apellido);
            propietario.setDni(dni);
            propietario.setTelefono(telefono);
            propietario.setEmail(email);

            mMensaje.postValue("Editar");
            mEstado.postValue(false);

            String token = ApiClient.leerToken(getApplication());
            ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
            Call llamada = api.actualizarPropietario("Bearer " + token,propietario);
            llamada.enqueue(new Callback<Propietario>(){
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if (response.isSuccessful()){
                        mPropietario.postValue(response.body());
                        Toast.makeText(getApplication(), "Propietario Actualizado", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplication(), "No se pudo actualizar el propietario", Toast.LENGTH_SHORT).show();
                        Log.d("Error", response.message());
                    }
                }
                @Override
                public void onFailure(Call<Propietario> call, Throwable t) {
                    Log.d("Error en el servidor", t.getMessage());
                }
            });
        }
    }
    public void leerPropietario(){
        String token = ApiClient.leerToken(getApplication());
        Call<Propietario> llamada = ApiClient.getApiInmobiliaria().obtenerPropietario("Bearer " + token);
        llamada.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()){
                    mPropietario.postValue(response.body());
                }else{
                    Toast.makeText(getApplication(), "No se pudo obtener el propietario", Toast.LENGTH_SHORT).show();

                    Log.d("Error", response.message());
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }

        });
    }
}