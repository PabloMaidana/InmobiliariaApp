package com.example.inmobiliariaapp.ui.inquilinos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.inmobiliariaapp.R;

public class DetalleInquilinoFragment extends Fragment {

    private DetalleInquilinoViewModel viewModel;

    private ImageView ivImagenInmueble;
    private TextView tvNombre, tvDni, tvTelefono, tvEmail,
            tvInicio, tvFin, tvMonto, tvDireccion;

    private static final String URLBASE = "https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_inquilino, container, false);
        viewModel = new ViewModelProvider(this).get(DetalleInquilinoViewModel.class);

        ivImagenInmueble = view.findViewById(R.id.ivImagenInmueble);
        tvNombre = view.findViewById(R.id.tvNombreInquilino);
        tvDni = view.findViewById(R.id.tvDniInquilino);
        tvTelefono = view.findViewById(R.id.tvTelefonoInquilino);
        tvEmail = view.findViewById(R.id.tvEmailInquilino);
        tvInicio = view.findViewById(R.id.tvFechaInicio);
        tvFin = view.findViewById(R.id.tvFechaFin);
        tvMonto = view.findViewById(R.id.tvMontoAlquiler);
        tvDireccion = view.findViewById(R.id.tvDireccionInmueble);

        int idInmueble = getArguments().getInt("idInmueble");

        viewModel.obtenerContratoPorInmueble(idInmueble);

        viewModel.getContrato().observe(getViewLifecycleOwner(), contrato -> {
            if (contrato != null) {
                tvNombre.setText("Nombre: " + contrato.getInquilino().getNombre() + " " + contrato.getInquilino().getApellido());
                tvDni.setText("DNI: " + contrato.getInquilino().getDni());
                tvTelefono.setText("Teléfono: " + contrato.getInquilino().getTelefono());
                tvEmail.setText("Email: " + contrato.getInquilino().getEmail());
                tvInicio.setText("Inicio: " + contrato.getFechaInicio());
                tvFin.setText("Finalización: " + contrato.getFechaFinalizacion());
                tvMonto.setText("Monto: $" + contrato.getMontoAlquiler());
                tvDireccion.setText("Dirección: " + contrato.getInmueble().getDireccion());

                Glide.with(this)
                        .load(URLBASE + contrato.getInmueble().getImagen())
                        .into(ivImagenInmueble);
            }
        });

        return view;
    }
}