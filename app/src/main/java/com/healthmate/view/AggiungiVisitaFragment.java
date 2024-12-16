package com.healthmate.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.healthmate.R;
import com.healthmate.database.AppDatabase;
import com.healthmate.database.bean.Visita;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

public class AggiungiVisitaFragment extends Fragment {
    public AggiungiVisitaFragment() {
        super(R.layout.fragment_aggiungi_visita);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CalendarView calendarView = view.findViewById(R.id.date_field);
        Spinner hourSpinner = view.findViewById(R.id.hour_field);
        Spinner locationSpinner = view.findViewById(R.id.select_location_field);
        Button submitButton = view.findViewById(R.id.submit_button);
        Instant currentInstant = Instant.now();

        // CalendarView configuration
        calendarView.setMinDate(currentInstant.toEpochMilli());
        calendarView.setMaxDate(currentInstant.plus(14, ChronoUnit.DAYS).toEpochMilli());

        // Hour Spinner configuration
        ArrayAdapter<CharSequence> hourAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.orari_visita,
                android.R.layout.simple_spinner_item
        );
        hourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hourSpinner.setAdapter(hourAdapter);

        // Location Spinner configuration
        ArrayAdapter<CharSequence> locationAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.luogo,
                android.R.layout.simple_spinner_item
        );
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(locationAdapter);

        // Button configuration
        submitButton.setOnClickListener((View v) -> { // Salva la prenotazione
            AppDatabase db = AppDatabase.getDatabase(requireContext());

            StringBuilder builder = new StringBuilder();
            String selectedDate = Instant.ofEpochMilli( calendarView.getDate() )
                    .atZone( ZoneId.systemDefault() )
                    .toLocalDate().toString();
            String selectedTime = (String) hourSpinner.getSelectedItem();
            String selectedLocation = (String) locationSpinner.getSelectedItem();

            // Instant object creation
            builder.append(selectedDate)
                    .append(' ')
                    .append(selectedTime);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                    "uuuu-MM-dd HH:mm",
                    Locale.getDefault()
            );
            Instant date = LocalDateTime.parse(builder.toString(), formatter)
                    .toInstant(ZoneOffset.UTC);


            db.getOperationExecutor().submit(() -> {
                db.prenotazioneDAO().insertPrenotazione(new Visita(
                        -1,
                        date,
                        selectedLocation,
                        1,
                        1
                ));

                view.post(() -> {
                   Snackbar.make(view, "Prenotazione effettuata correttamente",
                           BaseTransientBottomBar.LENGTH_LONG).show();
                });
            });
        });
    }
}