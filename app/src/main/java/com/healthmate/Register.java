package com.healthmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.healthmate.database.AppDatabase;
import com.healthmate.database.bean.Utente;
import com.healthmate.database.bean.Paziente;
import com.healthmate.database.bean.Medico;
import com.healthmate.AccountDAO;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;


public class Register extends AppCompatActivity {

    private RadioGroup userTypeRadioGroup;
    private LinearLayout patientLayout, doctorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);

        EditText NomeEditText = findViewById(R.id.NomeEditText);
        EditText CognomeEditText = findViewById(R.id.CognomeEditText);
        EditText NomeUtenteEditText = findViewById(R.id.NomeUtenteEditText);
        EditText CfEditText = findViewById(R.id.CfEditText);
        EditText DataNascitaEditText = findViewById(R.id.DataNascitaEditText);
        EditText IndirizzoEditText = findViewById(R.id.IndirizzoEditText);
        EditText MailEditText = findViewById(R.id.MailEditText);
        EditText TelefonoEditText = findViewById(R.id.TelefonoEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        EditText ConfermaPasswordEditText = findViewById(R.id.ConfermaPasswordEditText);
        Button RegisterButton = findViewById(R.id.RegisterButton);
        RadioButton maleRadioButton = findViewById(R.id.maleRadioButton);
        RadioButton femaleRadioButton = findViewById(R.id.femaleRadioButton);
        EditText tutoreEmailEditText = findViewById(R.id.tutoreEmailEditText);
        EditText studioEditText = findViewById(R.id.studioEditText);
        EditText numeroAlboEditText = findViewById(R.id.alboEditText);
        RadioButton doctorRadioButton = findViewById(R.id.doctorRadioButton);
        RadioButton patientRadioButton = findViewById(R.id.patientRadioButton);
        userTypeRadioGroup = findViewById(R.id.userTypeRadioGroup);
        patientLayout = findViewById(R.id.patientLayout);
        doctorLayout = findViewById(R.id.doctorLayout);

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

                AppDatabase db = AppDatabase.getDatabase(getApplicationContext());

                AccountDAO userDao = db.accountDAO();

                String name = NomeEditText.getText().toString();
                String Cognome = CognomeEditText.getText().toString();
                String username = NomeUtenteEditText.getText().toString();
                String CF = CfEditText.getText().toString();
                String dataNascitaString = DataNascitaEditText.getText().toString();
                LocalDate dataNascita = LocalDate.parse(dataNascitaString);
                Instant dataNascitaInstant = dataNascita.atStartOfDay(ZoneId.systemDefault()).toInstant();
                //Instant dataNascita = Instant.parse(DataNascitaEditText.getText().toString());
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

                /*
                 * FIXME: Le operazioni sul database vanno eseguite su un Thread separato.
                 *  Usare `db.getOperationExecutor().submit(() -> <Operazione da eseguire>)`.
                 */

                // Verifica se tutti i campi sono stati compilati
                if (doctorRadioButton.isChecked()) {
                    if (name.isEmpty() || Cognome.isEmpty() || CF.isEmpty() || dataNascitaString.isEmpty() ||
                            Indirizzo.isEmpty() || Mail.isEmpty() || NumeroTelefono.isEmpty() ||
                            username.isEmpty() || password.isEmpty() || ConfermaPassword.isEmpty() ||
                            emailTutore.isEmpty() || SessoSelezionato.isEmpty()) {

                        Toast.makeText(Register.this, "Tutti i campi devono essere compilati!", Toast.LENGTH_SHORT).show();
                        return; // Interrompe l'esecuzione se i campi non sono completi
                    }

// Verifica che il nome utente sia maggiore di 5 caratteri
                    if (username.length() <= 5) {
                        Toast.makeText(Register.this, "Il nome utente deve essere maggiore di 5 caratteri!", Toast.LENGTH_SHORT).show();
                        return;
                    }

// Verifica che il codice fiscale abbia esattamente 16 caratteri
                    if (CF.length() != 16) {
                        Toast.makeText(Register.this, "Il codice fiscale deve essere di 16 caratteri!", Toast.LENGTH_SHORT).show();
                        return;
                    }

// Verifica che la password sia maggiore di 8 caratteri
                    if (password.length() <= 8) {
                        Toast.makeText(Register.this, "La password deve essere maggiore di 8 caratteri!", Toast.LENGTH_SHORT).show();
                        return;
                    }

// Verifica che la password e la conferma siano uguali
                    if (!password.equals(ConfermaPassword)) {
                        Toast.makeText(Register.this, "La password e la conferma password devono coincidere!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        db.getOperationExecutor().submit(() -> {
                                userDao.insertPaziente(name, Cognome, CF, dataNascitaInstant,
                                    NumeroTelefono, Mail, username, password, SessoSelezionato, emailTutore);
                            v.post(() -> {
                                // Autenticazione riuscita
                                // Reindirizza l'utente alla schermata principale
                                Intent intent = new Intent(Register.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            });
                        });
                        Toast.makeText(Register.this, "Registrazione completata con successo!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(Register.this, "Errore durante la registrazione: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                // Verifica se tutti i campi sono stati compilati
                if(doctorRadioButton.isChecked()) {
                    if (name.isEmpty() || Cognome.isEmpty() || CF.isEmpty() || dataNascitaString.isEmpty() ||
                            Indirizzo.isEmpty() || Mail.isEmpty() || NumeroTelefono.isEmpty() ||
                            username.isEmpty() || password.isEmpty() || ConfermaPassword.isEmpty() ||
                            studio.isEmpty() || numeroAlbo.isEmpty()) {

                        Toast.makeText(Register.this, "Tutti i campi devono essere compilati!", Toast.LENGTH_SHORT).show();
                        return; // Interrompe l'esecuzione se i campi non sono completi
                    }

// Verifica che il nome utente sia maggiore di 5 caratteri
                    if (username.length() <= 5) {
                        Toast.makeText(Register.this, "Il nome utente deve essere maggiore di 5 caratteri!", Toast.LENGTH_SHORT).show();
                        return;
                    }

// Verifica che il codice fiscale abbia esattamente 16 caratteri
                    if (CF.length() != 16) {
                        Toast.makeText(Register.this, "Il codice fiscale deve essere di 16 caratteri!", Toast.LENGTH_SHORT).show();
                        return;
                    }

// Verifica che la password sia maggiore di 8 caratteri
                    if (password.length() <= 8) {
                        Toast.makeText(Register.this, "La password deve essere maggiore di 8 caratteri!", Toast.LENGTH_SHORT).show();
                        return;
                    }

// Verifica che la password e la conferma siano uguali
                    if (!password.equals(ConfermaPassword)) {
                        Toast.makeText(Register.this, "La password e la conferma password devono coincidere!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        db.getOperationExecutor().submit(() -> {
                                userDao.insertMedico(name, Cognome, CF, dataNascitaInstant,
                                    NumeroTelefono, Mail, username, password, SessoSelezionato, emailTutore);
                            v.post(() -> {
                                // Autenticazione riuscita
                                // Reindirizza l'utente alla schermata principale
                                Intent intent = new Intent(Register.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            });
                        });
                        Toast.makeText(Register.this, "Registrazione completata con successo!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(Register.this, "Errore durante la registrazione: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
