package com.healthmate;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.healthmate.controller.RefertoController;
import com.healthmate.database.bean.CartellaClinica;
import com.healthmate.database.bean.Medico;
import com.healthmate.database.bean.Paziente;
import com.healthmate.database.bean.Referto;
import com.healthmate.controller.RefertoAdapter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private RefertoController refertoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Medico m = new Medico(
                1,                                 // id
                "Giovanni",                        // nome
                "Ferrari",                         // cognome
                "FRRGNN80A01H501Z",                // codice fiscale
                Instant.parse("1980-05-15T00:00:00Z"), // data di nascita
                "+393401234567",                   // cellulare
                "giovanni.ferrari@example.com",    // email
                "giovanni.ferrari",                // username
                "password123",                     // password
                "Studio Medico Ferrari",           // studio
                "12345"                            // numero Albo
        );
        refertoController.saveMedico(m);

        Paziente p = new Paziente(
                1,                                 // id
                "Mario",                           // nome
                "Rossi",                           // cognome
                "RSSMRA85M01H501Z",                // cf
                Instant.parse("1985-01-01T00:00:00Z"), // dataNascita
                "+393401234567",                   // cellulare
                "mario.rossi@example.com",         // email
                "mario.rossi",                     // username
                "password123",                     // password
                "M",                               // sesso
                "tutore.rossi@example.com",        // emailTutore
                1                             // medicoId
        );
        refertoController.savePaziente(p);

        CartellaClinica c = new CartellaClinica(
                1,       // id
                5,       // numero referti
                10,      // spazio disponibile
                1,     // paziente_id
                1      // medico_id
        );
        refertoController.saveCartellaClinica(c);

        Referto r = new Referto(1,"Esame sangue","Esame sangue 11/12/2024","EsameSangue.pdf",1);
        refertoController.saveReferti(r);

        // Inizializza la lista
        List<Referto> refertiDao = refertoController.showReferti();

        // Configura il RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewReferti);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RefertoAdapter adapter = new RefertoAdapter(refertiDao);
        recyclerView.setAdapter(adapter);

        // Floating Action Button per aggiungere un referto
//        FloatingActionButton fabAddReferto = findViewById(R.id.fabAddReferto);
//        fabAddReferto.setOnClickListener(v -> {
//            referti.add("<< Referto " + (referti.size() + 1) + " >>");
//            adapter.notifyItemInserted(referti.size() - 1);
//        });
    }

}