package com.example.inmobiliariaapp.ui.pagos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inmobiliariaapp.R;
import com.example.inmobiliariaapp.model.Pago;

import java.util.List;

public class PagoAdapter extends RecyclerView.Adapter<PagoAdapter.PagoViewHolder> {

    private List<Pago> pagos;
    private Context context;

    public PagoAdapter(List<Pago> pagos, Context context) {
        this.pagos = pagos;
        this.context = context;
    }

    @NonNull
    @Override
    public PagoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pago_card, parent, false);
        return new PagoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PagoViewHolder holder, int position) {
        Pago pago = pagos.get(position);

        holder.tvDetalle.setText("Detalle: " + pago.getDetalle());
        holder.tvFechaPago.setText("Fecha: " + pago.getFechaPago());
        holder.tvMontoPago.setText("Monto: $" + pago.getMonto());
        holder.tvEstado.setText(pago.isEstado() ? "Estado: Pagado " : "Estado: Pendiente ");
    }

    @Override
    public int getItemCount() {
        return pagos.size();
    }

    public static class PagoViewHolder extends RecyclerView.ViewHolder {
        TextView tvDetalle, tvFechaPago, tvMontoPago, tvEstado;
        CardView cardViewPago;

        public PagoViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewPago = itemView.findViewById(R.id.cardViewPago);
            tvDetalle = itemView.findViewById(R.id.tvDetalle);
            tvFechaPago = itemView.findViewById(R.id.tvFechaPago);
            tvMontoPago = itemView.findViewById(R.id.tvMontoPago);
            tvEstado = itemView.findViewById(R.id.tvEstado);
        }
    }
}
