package com.healthmate.view.visite;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.healthmate.R;
import com.healthmate.controller.RefertoAdapter;
import com.healthmate.controller.RefertoController;
import com.healthmate.database.AppDatabase;
import com.healthmate.database.bean.Referto;
import com.healthmate.database.dao.CartellaClinicaDAO;
import com.healthmate.database.dao.MedicoDAO;
import com.healthmate.database.dao.PazienteDAO;

import java.util.List;

public class RefertoFragment extends Fragment {
    private RecyclerView recyclerViewReferti;
    private RefertoAdapter refertoAdapter;
    private RefertoController refertoController;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_referti, container, false);

        recyclerViewReferti = view.findViewById(R.id.recyclerViewReferti);
        recyclerViewReferti.setLayoutManager(new LinearLayoutManager(getContext()));

        // Importante: esegui operazioni di database fuori dal thread principale
        AppDatabase.getDatabase(requireContext()).getOperationExecutor().execute(() -> {
            CartellaClinicaDAO cartellaClinicaDAO = AppDatabase.getDatabase(requireContext()).cartellaClinicaDAO();
            MedicoDAO medicoDAO = AppDatabase.getDatabase(requireContext()).medicoDAO();
            PazienteDAO pazienteDAO = AppDatabase.getDatabase(requireContext()).pazienteDAO();

            refertoController = new RefertoController(cartellaClinicaDAO, medicoDAO, pazienteDAO);

            List<Referto> referti = refertoController.showReferti();

            // Aggiorna l'UI sul thread principale
            requireActivity().runOnUiThread(() -> {
                refertoAdapter = new RefertoAdapter(referti);
                recyclerViewReferti.setAdapter(refertoAdapter);
            });
        });

        return view;
    }
}