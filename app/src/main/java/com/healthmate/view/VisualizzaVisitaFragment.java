package com.healthmate.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavAction;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.healthmate.R;
import com.healthmate.database.AppDatabase;
import com.healthmate.database.bean.Visita;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

public class VisualizzaVisitaFragment extends Fragment {
    public VisualizzaVisitaFragment() {
        super(R.layout.fragment_con_visita);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView dateField = view.findViewById(R.id.date_field_visita);
        TextView timeField = view.findViewById(R.id.time_field_visita);
        TextView locationField = view.findViewById(R.id.location_field_visita);
        FloatingActionButton fab = view.findViewById(R.id.fab);

        AppDatabase db = AppDatabase.getDatabase(requireContext());

        // Aggiorna le TextView con i dati della Visita.
        db.getOperationExecutor().submit(() -> {
            Visita visita = db.prenotazioneDAO().queryVisitaPaziente(1);

            if (visita != null) {
                view.post(() -> {
                    ZonedDateTime dataVisita = visita.getData().atZone(ZoneId.systemDefault());

                    dateField.setText(dataVisita.toLocalDate().toString());
                    timeField.setText(dataVisita.toLocalTime().toString());
                    locationField.setText(visita.getLuogo());
                });
            }
        });

        // FAB Configuration
        fab.setOnClickListener((View v) -> {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.navigation_prenotazione_visita);
        });
    }
}
