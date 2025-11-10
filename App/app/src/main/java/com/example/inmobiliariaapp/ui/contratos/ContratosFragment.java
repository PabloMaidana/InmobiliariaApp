package com.example.inmobiliariaapp.ui.contratos;

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
import com.example.inmobiliariaapp.databinding.FragmentContratosBinding;

public class ContratosFragment extends Fragment {
    private FragmentContratosBinding binding;
    private ContratosViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentContratosBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(ContratosViewModel.class);

        viewModel.getInmueblesConContrato().observe(getViewLifecycleOwner(), inmuebles -> {
            ContratosAdapter adapter = new ContratosAdapter(inmuebles, getContext());
            binding.rvContratos.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.rvContratos.setAdapter(adapter);
        });

        return binding.getRoot();
    }
}