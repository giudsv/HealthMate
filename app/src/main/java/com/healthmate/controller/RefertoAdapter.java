package com.healthmate.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.healthmate.R;
import com.healthmate.database.bean.Referto;
import com.healthmate.view.visite.RefertoViewModel;

import java.util.List;

public class RefertoAdapter extends RecyclerView.Adapter<RefertoAdapter.RefertoViewHolder> {

    private List<Referto> referti;
    private RefertoViewModel refertoViewModel;
    private OnRefertoActionListener listener;

    public interface OnRefertoActionListener {
        void onModificaReferto(Referto referto);
        void onEliminaReferto(Referto referto);
    }

    public RefertoAdapter(List<Referto> referti, RefertoViewModel refertoViewModel, OnRefertoActionListener listener) {
        this.referti = referti;
        this.refertoViewModel = refertoViewModel;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RefertoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_referto, parent, false);
        return new RefertoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RefertoViewHolder holder, int position) {
        Referto referto = referti.get(position);
        holder.bind(referto);

        holder.btnModifica.setOnClickListener(v -> {
            if (listener != null) {
                listener.onModificaReferto(referto);
            }
        });

        holder.btnElimina.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEliminaReferto(referto);
            }
        });
    }

    @Override
    public int getItemCount() {
        return referti.size();
    }

    static class RefertoViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNomeReferto;
        private TextView textViewDescrizioneReferto;
        private TextView textViewAllegatoReferto;
        private Button btnModifica;
        private Button btnElimina;

        public RefertoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNomeReferto = itemView.findViewById(R.id.tvNome);
            textViewDescrizioneReferto = itemView.findViewById(R.id.tvDescrizione);
            textViewAllegatoReferto = itemView.findViewById(R.id.tvAllegato);
            btnModifica = itemView.findViewById(R.id.btnModifica);
            btnElimina = itemView.findViewById(R.id.btnElimina);
        }

        public void bind(Referto referto) {
            textViewNomeReferto.setText(referto.getNome());
            textViewDescrizioneReferto.setText(referto.getDescrizione());
            textViewAllegatoReferto.setText(referto.getAllegato());
        }
    }
}