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
    }

    @Override
    public int getItemCount() {
        return referti.size();
    }

    static class RefertoViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNomeReferto;
        private TextView textViewDescrizioneReferto;
        private TextView textViewAllegatoReferto;

        public RefertoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNomeReferto = itemView.findViewById(R.id.tvNome);
            textViewDescrizioneReferto = itemView.findViewById(R.id.tvDescrizione);
            textViewAllegatoReferto = itemView.findViewById(R.id.tvAllegato);
        }

        public void bind(Referto referto) {
            textViewNomeReferto.setText(referto.getNome());
            textViewDescrizioneReferto.setText(referto.getDescrizione());
            textViewAllegatoReferto.setText(referto.getAllegato());
        }
    }
    public List<Referto> getReferti() {
        return referti;
    }

    public void setReferti(List<Referto> referti) {
        this.referti = referti;
    }
}