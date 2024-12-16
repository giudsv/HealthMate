package com.healthmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.healthmate.database.AppDatabase;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class Register extends Fragment {

    private RadioGroup userTypeRadioGroup;
    private LinearLayout patientLayout, doctorLayout;

    public Register() {
        super(R.layout.activity_registrazione);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText NomeEditText = view.findViewById(R.id.NomeEditText);
        EditText CognomeEditText = view.findViewById(R.id.CognomeEditText);
        EditText NomeUtenteEditText = view.findViewById(R.id.NomeUtenteEditText);
        EditText CfEditText = view.findViewById(R.id.CfEditText);
        EditText DataNascitaEditText = view.findViewById(R.id.DataNascitaEditText);
        EditText IndirizzoEditText = view.findViewById(R.id.IndirizzoEditText);
        EditText MailEditText = view.findViewById(R.id.MailEditText);
        EditText TelefonoEditText = view.findViewById(R.id.TelefonoEditText);
        EditText passwordEditText = view.findViewById(R.id.passwordEditText);
        EditText ConfermaPasswordEditText = view.findViewById(R.id.ConfermaPasswordEditText);
        Button RegisterButton = view.findViewById(R.id.RegisterButton);
        RadioButton maleRadioButton = view.findViewById(R.id.maleRadioButton);
        RadioButton femaleRadioButton = view.findViewById(R.id.femaleRadioButton);
        EditText tutoreEmailEditText = view.findViewById(R.id.tutoreEmailEditText);
        EditText studioEditText = view.findViewById(R.id.studioEditText);
        EditText numeroAlboEditText = view.findViewById(R.id.alboEditText);
        RadioButton doctorRadioButton = view.findViewById(R.id.doctorRadioButton);
        RadioButton patientRadioButton = view.findViewById(R.id.patientRadioButton);
        userTypeRadioGroup = view.findViewById(R.id.userTypeRadioGroup);
        patientLayout = view.findViewById(R.id.patientLayout);
        doctorLayout = view.findViewById(R.id.doctorLayout);

        // Imposta il listener per il RadioGroup
        userTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Controlla quale RadioButton è selezionato
                if (checkedId == R.id.patientRadioButton) {
                    // Mostra il layout del paziente e nascondi quello del medico
                    patientLayout.setVisibility(View.VISIBLE);
                    doctorLayout.setVisibility(View.GONE);
                } else if (checkedId == R.id.doctorRadioButton) {
                    // Mostra il layout del medico e nascondi quello del paziente
                    doctorLayout.setVisibility(View.VISIBLE);
                    patientLayout.setVisibility(View.GONE);
                }
            }
        });

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppDatabase db = AppDatabase.getDatabase(requireContext().getApplicationContext());

                AccountDAO userDao = db.accountDAO();

                String name = NomeEditText.getText().toString();
                String Cognome = CognomeEditText.getText().toString();
                String username = NomeUtenteEditText.getText().toString();
                String CF = CfEditText.getText().toString();
                String dataNascitaString = DataNascitaEditText.getText().toString();
                LocalDate dataNascita = LocalDate.parse(dataNascitaString);
                Instant dataNascitaInstant = dataNascita.atStartOfDay(ZoneId.systemDefault()).toInstant();
                String Indirizzo = IndirizzoEditText.getText().toString();
                String Mail = MailEditText.getText().toString();
                String NumeroTelefono = TelefonoEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String ConfermaPassword = ConfermaPasswordEditText.getText().toString();
                String numeroAlbo = numeroAlboEditText.getText().toString();
                String studio = studioEditText.getText().toString();
                String emailTutore = tutoreEmailEditText.getText().toString();
                String SessoSelezionato;

                if (maleRadioButton.isChecked()) {
                    SessoSelezionato = maleRadioButton.getText().toString(); // "Maschio" o il testo del RadioButton
                } else if (femaleRadioButton.isChecked()) {
                    SessoSelezionato = femaleRadioButton.getText().toString(); // "Femmina" o il testo del RadioButton
                } else {
                    SessoSelezionato = "";
                }

                // Verifica se tutti i campi sono stati compilati
                if (patientRadioButton.isChecked()) {
                    if (name.isEmpty() || Cognome.isEmpty() || CF.isEmpty() || dataNascitaString.isEmpty() ||
                            Indirizzo.isEmpty() || Mail.isEmpty() || NumeroTelefono.isEmpty() ||
                            username.isEmpty() || password.isEmpty() || ConfermaPassword.isEmpty() ||
                            emailTutore.isEmpty() || SessoSelezionato.isEmpty()) {

                        Toast.makeText(requireContext(), "Tutti i campi devono essere compilati!", Toast.LENGTH_SHORT).show();
                        return; // Interrompe l'esecuzione se i campi non sono completi
                    }

                    if (username.length() <= 5) {
                        Toast.makeText(requireContext(), "Il nome utente deve essere maggiore di 5 caratteri!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (CF.length() != 16) {
                        Toast.makeText(requireContext(), "Il codice fiscale deve essere di 16 caratteri!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (password.length() <= 8) {
                        Toast.makeText(requireContext(), "La password deve essere maggiore di 8 caratteri!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!password.equals(ConfermaPassword)) {
                        Toast.makeText(requireContext(), "La password e la conferma password devono coincidere!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        db.getOperationExecutor().submit(() -> {
                            userDao.insertPaziente(name, Cognome, CF, dataNascitaInstant,
                                    NumeroTelefono, Mail, username, password, SessoSelezionato, emailTutore);
                            requireActivity().runOnUiThread(() -> {
                                Intent intent = new Intent(requireContext(), MainActivity.class);
                                startActivity(intent);
                                requireActivity().finish();
                            });
                        });
                        Toast.makeText(requireContext(), "Registrazione completata con successo!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(requireContext(), "Errore durante la registrazione: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                if (doctorRadioButton.isChecked()) {
                    if (name.isEmpty() || Cognome.isEmpty() || CF.isEmpty() || dataNascitaString.isEmpty() ||
                            Indirizzo.isEmpty() || Mail.isEmpty() || NumeroTelefono.isEmpty() ||
                            username.isEmpty() || password.isEmpty() || ConfermaPassword.isEmpty() ||
                            studio.isEmpty() || numeroAlbo.isEmpty()) {

                        Toast.makeText(requireContext(), "Tutti i campi devono essere compilati!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (username.length() <= 5) {
                        Toast.makeText(requireContext(), "Il nome utente deve essere maggiore di 5 caratteri!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (CF.length() != 16) {
                        Toast.makeText(requireContext(), "Il codice fiscale deve essere di 16 caratteri!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (password.length() <= 8) {
                        Toast.makeText(requireContext(), "La password deve essere maggiore di 8 caratteri!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!password.equals(ConfermaPassword)) {
                        Toast.makeText(requireContext(), "La password e la conferma password devono coincidere!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        db.getOperationExecutor().submit(() -> {
                            userDao.insertMedico(name, Cognome, CF, dataNascitaInstant,
                                    NumeroTelefono, Mail, username, password, SessoSelezionato, emailTutore);
                            requireActivity().runOnUiThread(() -> {
                                Intent intent = new Intent(requireContext(), MainActivity.class);
                                startActivity(intent);
                                requireActivity().finish();
                            });
                        });
                        Toast.makeText(requireContext(), "Registrazione completata con successo!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(requireContext(), "Errore durante la registrazione: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
