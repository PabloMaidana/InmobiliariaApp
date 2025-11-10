package com.example.inmobiliariaapp.ui.inquilinos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.inmobiliariaapp.R;
import com.example.inmobiliariaapp.model.Inmueble;

import java.util.List;

public class InquilinosAdapter extends RecyclerView.Adapter<InquilinosAdapter.InquilinoViewHolder> {

    private List<Inmueble> inmuebles;
    private Context context;
    private static final String URLBASE = "https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/";

    public InquilinosAdapter(List<Inmueble> inmuebles, Context context) {
        this.inmuebles = inmuebles;
        this.context = context;
    }

    @NonNull
    @Override
    public InquilinoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.inquilino_card, parent, false);
        return new InquilinoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InquilinoViewHolder holder, int position) {
        Inmueble inmueble = inmuebles.get(position);

        holder.tvDireccion.setText(inmueble.getDireccion());
        holder.tvTipo.setText(inmueble.getTipo());
        holder.tvUso.setText(inmueble.getUso());

        Glide.with(context)
                .load(URLBASE + inmueble.getImagen())
                .placeholder(null)
                .error("null")
                .into(holder.img);

        holder.cardView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("idInmueble", inmueble.getIdInmueble());
            Navigation.findNavController(v).navigate(R.id.detalleInquilinoFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return inmuebles.size();
    }

    public static class InquilinoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDireccion, tvTipo, tvUso;
        private ImageView img;
        private CardView cardView;

        public InquilinoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvTipo = itemView.findViewById(R.id.tvTipo);
            tvUso = itemView.findViewById(R.id.tvUso);
            img = itemView.findViewById(R.id.img);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
