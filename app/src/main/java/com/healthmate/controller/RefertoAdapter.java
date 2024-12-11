package com.healthmate.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.healthmate.R;
import com.healthmate.database.bean.Referto;

import java.util.List;

public class RefertoAdapter extends RecyclerView.Adapter<RefertoAdapter.RefertoViewHolder> {

    private List<Referto> referti;

    public RefertoAdapter(List<Referto> referti) {
        this.referti = referti;
    }

    @NonNull
    public void RefertoViewHolder(@NonNull View itemView) {
        TextView tvNome, tvDescrizione, tvAllegato;
        tvNome = itemView.findViewById(R.id.tvNome);
        tvDescrizione = itemView.findViewById(R.id.tvDescrizione);
        tvAllegato = itemView.findViewById(R.id.tvAllegato);
    }

    @NonNull
    @Override
    public RefertoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RefertoViewHolder holder, int position) {
        Referto referto = referti.get(position);
        holder.tvNome.setText(referto.getNome());
        holder.tvDescrizione.setText(referto.getDescrizione());
        holder.tvAllegato.setText(referto.getAllegato());
    }

    @Override
    public int getItemCount() {
        return referti.size();
    }

    public static class RefertoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNome, tvDescrizione, tvAllegato;

        public RefertoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tvNome);
            tvDescrizione = itemView.findViewById(R.id.tvDescrizione);
            tvAllegato = itemView.findViewById(R.id.tvAllegato);
        }
    }
}