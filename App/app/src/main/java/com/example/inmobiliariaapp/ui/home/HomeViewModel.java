package com.example.inmobiliariaapp.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<MapaActual> mMapaActual;

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<MapaActual> getmMapaActual() {
        if (mMapaActual == null){
            mMapaActual = new MutableLiveData<>();
        }
        return mMapaActual;
    }

    public void cargarMapa(){
        MapaActual mapaActual = new MapaActual();
        mMapaActual.setValue(mapaActual);
    }

    public class MapaActual implements OnMapReadyCallback {
        LatLng sanLuis = new LatLng(-33.27020697272328, -66.32418954876677);
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            MarkerOptions marcadorSanLuis = new MarkerOptions();
            marcadorSanLuis.position(sanLuis);
            marcadorSanLuis.title("Inmobiliaria La Punta");

            googleMap.addMarker(marcadorSanLuis);
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            CameraPosition cameraPosition = new CameraPosition
                    .Builder()
                    .target(sanLuis)
                    .zoom(30)
                    .bearing(45)
                    .tilt(15).build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            googleMap.animateCamera(cameraUpdate);
        }
    }
}