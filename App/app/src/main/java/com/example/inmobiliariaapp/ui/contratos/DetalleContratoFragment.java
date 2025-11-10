package com.example.inmobiliariaapp.ui.contratos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.inmobiliariaapp.R;

public class DetalleContratoFragment extends Fragment {
    private DetalleContratoViewModel viewModel;
    private ImageView ivInmueble;
    private TextView tvInicio, tvFin, tvMonto, tvDireccion;
    private Button btnPagos;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_contrato, container, false);
        viewModel = new ViewModelProvider(this).get(DetalleContratoViewModel.class);

        ivInmueble = view.findViewById(R.id.ivInmueble);
        tvInicio = view.findViewById(R.id.tvFechaInicio);
        tvFin = view.findViewById(R.id.tvFechaFin);
        tvMonto = view.findViewById(R.id.tvMonto);
        tvDireccion = view.findViewById(R.id.tvDireccion);
        btnPagos = view.findViewById(R.id.btnPagos);

        int idInmueble = getArguments().getInt("idInmueble");
        viewModel.obtenerContratoPorInmueble(idInmueble);

        viewModel.getContrato().observe(getViewLifecycleOwner(), contrato -> {
            if (contrato != null) {
                tvInicio.setText("Inicio: " + contrato.getFechaInicio());
                tvFin.setText("Finalización: " + contrato.getFechaFinalizacion());
                tvMonto.setText("Monto: $" + contrato.getMontoAlquiler());
                tvDireccion.setText("Dirección: " + contrato.getInmueble().getDireccion());

                Glide.with(this)
                        .load("https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/" + contrato.getInmueble().getImagen())
                        .into(ivInmueble);

                btnPagos.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putInt("idContrato", contrato.getIdContrato());
                    Navigation.findNavController(v).navigate(R.id.pagosFragment, bundle);
                });
            }
        });

        return view;
    }
}