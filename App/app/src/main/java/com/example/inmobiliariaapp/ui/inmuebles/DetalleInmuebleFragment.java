package com.example.inmobiliariaapp.ui.inmuebles;

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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.inmobiliariaapp.R;
import com.example.inmobiliariaapp.databinding.FragmentDetalleInmuebleBinding;
import com.example.inmobiliariaapp.model.Inmueble;

public class DetalleInmuebleFragment extends Fragment {

    private static final String URLBASE = "https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/";
    private FragmentDetalleInmuebleBinding binding;
    private DetalleInmuebleViewModel viewModel;
    private Inmueble inmuebleActual;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentDetalleInmuebleBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);

        Bundle args = getArguments();
        if (args != null) {
            inmuebleActual = new Inmueble();
            inmuebleActual.setIdInmueble(args.getInt("idInmueble"));
            inmuebleActual.setDireccion(args.getString("direccion"));
            inmuebleActual.setUso(args.getString("uso"));
            inmuebleActual.setTipo(args.getString("tipo"));
            inmuebleActual.setAmbientes(args.getInt("ambientes"));
            inmuebleActual.setSuperficie(args.getInt("superficie"));
            inmuebleActual.setValor(args.getDouble("valor"));
            inmuebleActual.setDisponible(args.getBoolean("disponible"));
            inmuebleActual.setImagen(args.getString("imagen"));


            binding.tvDireccion.setText("Dirección: " + inmuebleActual.getDireccion());
            binding.tvUso.setText("Uso: " + inmuebleActual.getUso());
            binding.tvTipo.setText("Tipo: " + inmuebleActual.getTipo());
            binding.tvAmbientes.setText("Ambientes: " + inmuebleActual.getAmbientes());
            binding.tvSuperficie.setText("Superficie: " + inmuebleActual.getSuperficie() + " m²");
            binding.tvValor.setText("Precio: $" + inmuebleActual.getValor());
            binding.tvDisponible.setText(inmuebleActual.isDisponible() ? "Disponible" : "No disponible");
            binding.swDisponible.setChecked(inmuebleActual.isDisponible());

            String imagenUrl = URLBASE + inmuebleActual.getImagen();
            Glide.with(this)
                    .load(imagenUrl)
                    .placeholder(null)
                    .error("null")
                    .into(binding.ivImagen);
        }

        viewModel.getMensaje().observe(getViewLifecycleOwner(), msg ->
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        );

        binding.swDisponible.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (inmuebleActual != null) {
                inmuebleActual.setDisponible(isChecked);
                binding.tvDisponible.setText(isChecked ? "Disponible" : "No disponible");

                viewModel.actualizarInmueble(inmuebleActual);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}