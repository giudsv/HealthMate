package com.healthmate.view.visite;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.healthmate.R;
import com.healthmate.controller.RefertoAdapter;
import com.healthmate.controller.RefertoController;
import com.healthmate.database.AppDatabase;
import com.healthmate.database.bean.Referto;
import com.healthmate.database.dao.CartellaClinicaDAO;
import com.healthmate.database.dao.MedicoDAO;
import com.healthmate.database.dao.PazienteDAO;
import com.healthmate.databinding.FragmentRefertiBinding;

import java.util.ArrayList;
import java.util.List;

public class RefertoFragment extends Fragment implements AddRefertoDialogFragment.AddRefertoListener {
    private RecyclerView recyclerViewReferti;
    private RefertoAdapter refertoAdapter;
    private RefertoController refertoController;
    private List<Referto> referti = new ArrayList<>();
    private FragmentRefertiBinding binding;
    private FloatingActionButton fabAddReferto;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Usa il binding invece di inflate manuale
        binding = FragmentRefertiBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        recyclerViewReferti = binding.recyclerViewReferti;
        recyclerViewReferti.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerView recyclerViewReferti = view.findViewById(R.id.recyclerViewReferti);
        TextView textViewNoReferti = view.findViewById(R.id.textViewNoReferti);

        RefertoViewModel.Factory factory = new RefertoViewModel.Factory((Application) requireContext().getApplicationContext());
        RefertoViewModel refertoViewModel = new ViewModelProvider(this, factory).get(RefertoViewModel.class);

        AppDatabase.getDatabase(requireContext()).getOperationExecutor().execute(() -> {
            CartellaClinicaDAO cartellaClinicaDAO = AppDatabase.getDatabase(requireContext()).cartellaClinicaDAO();
            MedicoDAO medicoDAO = AppDatabase.getDatabase(requireContext()).medicoDAO();
            PazienteDAO pazienteDAO = AppDatabase.getDatabase(requireContext()).pazienteDAO();

            refertoController = new RefertoController(cartellaClinicaDAO, medicoDAO, pazienteDAO);

            referti = refertoController.showReferti();

            // Aggiorna l'UI sul thread principale
            requireActivity().runOnUiThread(() -> {
                refertoAdapter = new RefertoAdapter(referti);
                recyclerViewReferti.setAdapter(refertoAdapter);
                System.out.println("recycler" + recyclerViewReferti.findViewById(R.id.tvNome));
                recyclerViewReferti.setVisibility(View.VISIBLE);
            });
        });

//        if(referti.isEmpty()){
//            textViewNoReferti.setVisibility(View.VISIBLE);
//            recyclerViewReferti.setVisibility(View.GONE);
//        } else {
//            textViewNoReferti.setVisibility(View.GONE);
//            recyclerViewReferti.setVisibility(View.VISIBLE);
//
//            refertoAdapter = new RefertoAdapter(referti);
//            recyclerViewReferti.setAdapter(refertoAdapter);
//            recyclerViewReferti.setLayoutManager(new LinearLayoutManager(requireContext()));
//        }

        // Usa il binding per trovare il FAB
        fabAddReferto = binding.fabAddReferto;
        fabAddReferto.setOnClickListener(v -> {
            AddRefertoDialogFragment dialogFragment = new AddRefertoDialogFragment();
            dialogFragment.setTargetFragment(this, 0);
            dialogFragment.show(getParentFragmentManager(), "AddRefertoDialog");
        });
        return view;
    }

    public void onRefertoAdded(Referto referto) {
        // Aggiorna la lista dei referti
        if (referti != null) {
            referti.add(referto);
            refertoAdapter.notifyItemInserted(referti.size() - 1);
        } else {
            // Se la lista Ã¨ vuota, ricarica tutti i referti
            caricaReferti();
        }
    }

    private void caricaReferti() {
        AppDatabase.getDatabase(requireContext()).getOperationExecutor().execute(() -> {
            CartellaClinicaDAO cartellaClinicaDAO = AppDatabase.getDatabase(requireContext()).cartellaClinicaDAO();
            MedicoDAO medicoDAO = AppDatabase.getDatabase(requireContext()).medicoDAO();
            PazienteDAO pazienteDAO = AppDatabase.getDatabase(requireContext()).pazienteDAO();

            RefertoController refertoController = new RefertoController(cartellaClinicaDAO, medicoDAO, pazienteDAO);

            referti = refertoController.showReferti();

            requireActivity().runOnUiThread(() -> {
                refertoAdapter = new RefertoAdapter(referti);
                recyclerViewReferti.setAdapter(refertoAdapter);
            });
        });
    }

    public void onDestroyView() {
        super.onDestroyView();
        // Importante: azzera il binding quando la view viene distrutta
        binding = null;
    }
}