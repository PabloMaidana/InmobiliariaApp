package com.example.inmobiliariaapp.ui.inquilinos;

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
import com.example.inmobiliariaapp.databinding.FragmentInquilinosBinding;

public class InquilinosFragment extends Fragment {

    private FragmentInquilinosBinding binding;
    private InquilinosViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInquilinosBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(InquilinosViewModel.class);

        viewModel.getInmueblesAlquilados().observe(getViewLifecycleOwner(), inmuebles -> {
            InquilinosAdapter adapter = new InquilinosAdapter(inmuebles, getContext());
            binding.rvInquilinos.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.rvInquilinos.setAdapter(adapter);
        });

        return binding.getRoot();
    }

}