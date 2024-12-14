package com.healthmate.view.visite;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.healthmate.controller.RefertoAdapter;
import com.healthmate.controller.RefertoController;
import com.healthmate.database.bean.Referto;
import com.healthmate.databinding.FragmentRefertiBinding;

import java.util.ArrayList;
import java.util.List;

public class RefertoFragment extends Fragment
        implements AddRefertoDialogFragment.AddRefertoListener,
        RefertoAdapter.OnRefertoActionListener {

    private RecyclerView recyclerViewReferti;
    private RefertoAdapter refertoAdapter;
    private RefertoController refertoController;
    private List<Referto> referti = new ArrayList<>();
    private FragmentRefertiBinding binding;
    private RefertoViewModel refertoViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRefertiBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Initialize ViewModel
        RefertoViewModel.Factory factory = new RefertoViewModel.Factory(
                (Application) requireContext().getApplicationContext()
        );
        refertoViewModel = new ViewModelProvider(this, factory).get(RefertoViewModel.class);

        // Set up RecyclerView
        setupRecyclerView();
        // Set up ViewModel to observe referti data
        setupViewModel();
        // Set up Floating Action Button to add a new referto
        setupFab();

        return view;
    }

    private void setupRecyclerView() {
        recyclerViewReferti = binding.recyclerViewReferti;
        recyclerViewReferti.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupViewModel() {
        refertoViewModel.getReferti().observe(getViewLifecycleOwner(), refertiList -> {
            referti = refertiList;
            if (referti.isEmpty()) {
                binding.textViewNoReferti.setVisibility(View.VISIBLE);
                recyclerViewReferti.setVisibility(View.GONE);
            } else {
                binding.textViewNoReferti.setVisibility(View.GONE);
                recyclerViewReferti.setVisibility(View.VISIBLE);

                refertoAdapter = new RefertoAdapter(referti, refertoViewModel, this);
                recyclerViewReferti.setAdapter(refertoAdapter);
            }
        });
    }

    private void setupFab() {
        binding.fabAddReferto.setOnClickListener(v -> {
            AddRefertoDialogFragment dialogFragment = new AddRefertoDialogFragment();
            dialogFragment.setTargetFragment(this, 0);
            dialogFragment.show(getParentFragmentManager(), "AddRefertoDialog");
        });
    }

    @Override
    public void onRefertoAdded(Referto referto) {
        refertoViewModel.addReferto(referto);
    }

    @Override
    public void onRefertoUpdated(Referto referto) {
        refertoViewModel.updateReferto(referto);
    }

    @Override
    public void onModificaReferto(Referto referto) {
        AddRefertoDialogFragment dialogFragment = AddRefertoDialogFragment.newInstance(referto);
        dialogFragment.setTargetFragment(this, 0); // Per ricevere la callback
        dialogFragment.show(getParentFragmentManager(), "EditRefertoDialog");
    }

    @Override
    public void onEliminaReferto(Referto referto) {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Elimina Referto")
                .setMessage("Sei sicuro di voler eliminare questo referto?")
                .setPositiveButton("Elimina", (dialog, which) -> {
                    refertoViewModel.deleteReferto(referto);
                    Toast.makeText(requireContext(), "Referto eliminato", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Annulla", null)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Cleanup to prevent memory leaks when the fragment's view is destroyed
        binding = null;
    }
}
