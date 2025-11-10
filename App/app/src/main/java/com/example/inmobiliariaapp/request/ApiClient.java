package com.example.inmobiliariaapp.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.inmobiliariaapp.model.Contrato;
import com.example.inmobiliariaapp.model.Inmueble;
import com.example.inmobiliariaapp.model.Pago;
import com.example.inmobiliariaapp.model.Propietario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class ApiClient {
    private static final String URLBASE = "https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/";


    public static InmobiliariaService getApiInmobiliaria(){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        Retrofit retroFit = new Retrofit.Builder()
                .baseUrl(URLBASE)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retroFit.create(InmobiliariaService.class);

    }

    public static void guardarToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public static String leerToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        return sp.getString("token", null);
    }

    public interface InmobiliariaService{
        @FormUrlEncoded
        @POST("api/Propietarios/login")
        Call<String>
        login(@Field("Usuario") String usuario, @Field("Clave") String clave);
        @GET("api/Propietarios")
        Call<Propietario> obtenerPropietario(@Header("Authorization") String token);
        @PUT("api/Propietarios/actualizar")
        Call<Propietario> actualizarPropietario(@Header("Authorization") String token, @Body Propietario propietario);
        @GET("api/Inmuebles")
        Call<List<Inmueble>> obtenerInmuebles(@Header("Authorization") String token);
        @Multipart
        @POST("api/Inmuebles/cargar")
        Call<Inmueble> agregarInmueble(
                @Header("Authorization") String token,
                @Part MultipartBody.Part imagen,
                @Part("inmueble") RequestBody inmueble
        );
        @GET("api/Inmuebles/GetContratoVigente")
        Call<List<Inmueble>> obtenerInmueblesConContratoVigente(@Header("Authorization") String token);
        @GET("api/contratos/inmueble/{id}")
        Call<Contrato> obtenerContratoPorInmueble(@Header("Authorization") String token, @Path("id") int idInmueble);
        @GET("api/pagos/contrato/{id}")
        Call<List<Pago>> obtenerPagosPorContrato(@Header("Authorization") String token, @Path("id") int idContrato);
        @PUT("api/Inmuebles/actualizar")
        Call<Inmueble> actualizarInmueble(
                @Header("Authorization") String token,
                @Body Inmueble inmueble
        );
        @FormUrlEncoded
        @PUT("api/Propietarios/changePassword")
        Call<Void> cambiarPassword(
                @Header("Authorization") String token,
                @Field("currentPassword") String actual,
                @Field("newPassword") String nueva
        );
        @FormUrlEncoded
        @POST("api/Propietarios/email")
        Call<String> resetearPassword(@Field("email") String email);
    }
}
