package com.healthmate.view.visite;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.healthmate.R;
import com.healthmate.controller.RefertoAdapter;
import com.healthmate.view.visite.RefertoViewModel;

public class RefertoFragment extends Fragment {
    private RefertoViewModel refertoViewModel;
    private RefertoAdapter refertoAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_referti, container, false);

        RecyclerView recyclerViewReferti = view.findViewById(R.id.recyclerViewReferti);
        TextView textViewNoReferti = view.findViewById(R.id.textViewNoReferti);

        // Inizializzazione del ViewModel
        RefertoViewModel.Factory factory = new RefertoViewModel.Factory((Application) requireContext().getApplicationContext());
        refertoViewModel = new ViewModelProvider(this, factory).get(RefertoViewModel.class);

        // Osservazione dei dati LiveData
        refertoViewModel.getReferti().observe(getViewLifecycleOwner(), referti -> {
            if (referti.isEmpty()) {
                textViewNoReferti.setVisibility(View.VISIBLE);
                recyclerViewReferti.setVisibility(View.GONE);
            } else {
                textViewNoReferti.setVisibility(View.GONE);
                recyclerViewReferti.setVisibility(View.VISIBLE);

                refertoAdapter = new RefertoAdapter(referti);
                recyclerViewReferti.setAdapter(refertoAdapter);
                recyclerViewReferti.setLayoutManager(new LinearLayoutManager(requireContext()));
            }
        });

        return view;
    }
}
