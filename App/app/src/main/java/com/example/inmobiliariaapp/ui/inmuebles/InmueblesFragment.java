package com.example.inmobiliariaapp.ui.inmuebles;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliariaapp.R;
import com.example.inmobiliariaapp.databinding.ActivityMainBinding;
import com.example.inmobiliariaapp.databinding.FragmentInmueblesBinding;
import com.example.inmobiliariaapp.model.Inmueble;

import java.util.List;

public class InmueblesFragment extends Fragment {
    private InmueblesViewModel viewModel;
    private FragmentInmueblesBinding binding;
    public static InmueblesFragment newInstance() {
        return new InmueblesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(InmueblesViewModel.class);
        binding = FragmentInmueblesBinding.inflate(getLayoutInflater());

        viewModel.getmInmuebles().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                InmuebleAdapter adapter = new InmuebleAdapter(inmuebles, getContext());
                LinearLayoutManager llm = new LinearLayoutManager(getContext());
                RecyclerView rvInmuebles = binding.rvInmuebles;

                binding.rvInmuebles.setLayoutManager(llm);
                binding.rvInmuebles.setAdapter(adapter);
            }
        });
        viewModel.leerInmuebles();

        binding.fab.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.agregarInmuebleFragment);
        });

        return binding.getRoot();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}