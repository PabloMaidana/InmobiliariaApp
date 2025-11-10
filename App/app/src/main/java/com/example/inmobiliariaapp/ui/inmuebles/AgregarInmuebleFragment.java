package com.example.inmobiliariaapp.ui.inmuebles;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliariaapp.R;
import com.example.inmobiliariaapp.databinding.FragmentAgregarInmuebleBinding;

public class AgregarInmuebleFragment extends Fragment {
    private FragmentAgregarInmuebleBinding binding;
    private AgregarInmuebleViewModel mViewModel;
    private ActivityResultLauncher<Intent> arl;
    private Intent intent;

    public static AgregarInmuebleFragment newInstance() {
        return new AgregarInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(AgregarInmuebleViewModel.class);
        binding = FragmentAgregarInmuebleBinding.inflate(getLayoutInflater());

        abrirGaleria();

        binding.btnSeleccionarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arl.launch(intent);
            }
        });

        mViewModel.getMUri().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                binding.ivPreview.setImageURI(uri);
            }
        });

        binding.btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.cargarInmueble(
                        binding.etDireccion.getText().toString(),
                        binding.etUso.getText().toString(),
                        binding.etTipo.getText().toString(),
                        Integer.parseInt(binding.etAmbientes.getText().toString()),
                        Integer.parseInt(binding.etSuperficie.getText().toString()),
                        Double.parseDouble(binding.etValor.getText().toString()),
                        binding.cbDisponible.isChecked(),
                        mViewModel.getMUri().getValue()

                );
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AgregarInmuebleViewModel.class);
        // TODO: Use the ViewModel
    }

    private void abrirGaleria(){
        intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                mViewModel.recibirFoto(result);
            }
        });
    }
}