package com.example.inmobiliariaapp.ui.inmuebles;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliariaapp.model.Inmueble;
import com.example.inmobiliariaapp.request.ApiClient;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class AgregarInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Uri> mUri = new MutableLiveData<>();

    public AgregarInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Uri> getMUri(){
        return mUri;
    }

    public void recibirFoto(ActivityResult result){
        if (result.getResultCode() == Activity.RESULT_OK){
            Intent data = result.getData();
            Uri uri = data.getData();
            mUri.setValue(uri);
        }
    }

    public void cargarInmueble(String direccion, String uso, String tipo, int ambientes, int superficie, double valor, boolean disponible, Uri uri){

        if (direccion.isEmpty() || uso.isEmpty() || tipo.isEmpty() || ambientes == 0 || superficie == 0 || valor == 0){
            Toast.makeText(getApplication(), "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if(mUri.getValue() == null){
            Toast.makeText(getApplication(), "Debe seleccionar una imagen", Toast.LENGTH_SHORT).show();
            return ;
        }

        Inmueble inmueble = new Inmueble();
        inmueble.setDireccion(direccion);
        inmueble.setUso(uso);
        inmueble.setTipo(tipo);
        inmueble.setAmbientes(ambientes);
        inmueble.setSuperficie(superficie);
        inmueble.setValor(valor);
        inmueble.setDisponible(disponible);

        byte[] imagen = transformarImagen();
        String inmuebleJson = new Gson().toJson(inmueble);
        RequestBody inmuebleBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), inmuebleJson);
        RequestBody imagenBody = RequestBody.create(MediaType.parse("image/jpeg"), imagen);

        MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen", "imagen.jpg", imagenBody);

        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        String token = ApiClient.leerToken(getApplication());
        Call<Inmueble> llamada = api.agregarInmueble("Bearer " + token, imagenPart, inmuebleBody);

        llamada.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getApplication(), "Inmueble agregado", Toast.LENGTH_SHORT).show();
                } else {
                    // ================== CRITICAL DEBUGGING CODE ==================
                    String errorBodyString = "No body";
                    try {
                        // Read the error body from the server response
                        errorBodyString = response.errorBody().string();
                    } catch (Exception e) {
                        Log.e("API_ERROR", "Error parsing error body", e);
                    }

                    Log.d("API_ERROR", "Code: " + response.code()); // e.g., 400, 401, 500
                    Log.d("API_ERROR", "Message: " + response.message()); // e.g., "Bad Request", "Unauthorized"
                    Log.d("API_ERROR", "Error Body: " + errorBodyString); // The actual JSON error from the server

                    Toast.makeText(getApplication(), "Error: " + response.code(), Toast.LENGTH_LONG).show();
                    // =============================================================
                }
            }
            @Override
            public void onFailure(Call<Inmueble> call, Throwable t){
                Toast.makeText(getApplication(), "Error de servidor", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private byte[] transformarImagen(){
        try{
            Uri uri = mUri.getValue();
            InputStream inputStream = getApplication().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();

        }catch (FileNotFoundException e){
            Toast.makeText(getApplication(), "No ha seleccionado una foto", Toast.LENGTH_SHORT).show();
            return new byte[]{};
        }
    }

}