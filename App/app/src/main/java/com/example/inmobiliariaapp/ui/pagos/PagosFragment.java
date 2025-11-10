package com.example.inmobiliariaapp.ui.pagos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliariaapp.R;
import com.example.inmobiliariaapp.databinding.FragmentPagosBinding;

public class PagosFragment extends Fragment {

    private FragmentPagosBinding binding;
    private PagosViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPagosBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(PagosViewModel.class);

        int idContrato = getArguments().getInt("idContrato");

        viewModel.getPagos().observe(getViewLifecycleOwner(), pagos -> {
            if (pagos != null && !pagos.isEmpty()) {
                PagoAdapter adapter = new PagoAdapter(pagos, getContext());
                binding.rvPagos.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.rvPagos.setAdapter(adapter);
            }
        });

        viewModel.obtenerPagosPorContrato(idContrato);

        return binding.getRoot();
    }
}