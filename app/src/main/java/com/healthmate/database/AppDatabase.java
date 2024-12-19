package com.healthmate.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.healthmate.database.bean.CartellaClinica;
import com.healthmate.AccountDAO;
import com.healthmate.database.bean.Medico;
import com.healthmate.database.bean.Paziente;
import com.healthmate.database.bean.Referto;
import com.healthmate.database.bean.Visita;
import com.healthmate.database.dao.CartellaClinicaDAO;
import com.healthmate.database.dao.MedicoDAO;
import com.healthmate.database.dao.PazienteDAO;
import com.healthmate.database.dao.PrenotazioneDAO;

import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TypeConverters(value = {Converters.class})
@Database (
        version = 2,
        entities = {
                Medico.class,
                Paziente.class,
                Visita.class,
                CartellaClinica.class,
                Referto.class
        }
)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE = null;
    private static final String DATABASE_NAME = "db-1.3.0";
    private final ExecutorService operationExecutor = Executors.newSingleThreadExecutor();

    public static synchronized AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            // Esegui il popolamento in un thread separato
                            Executors.newSingleThreadExecutor().execute(() -> {
                                MedicoDAO medicoDAO = INSTANCE.medicoDAO();
                                PazienteDAO pazienteDAO = INSTANCE.pazienteDAO();
                                CartellaClinicaDAO cartellaClinicaDAO = INSTANCE.cartellaClinicaDAO();
                                AccountDAO accountDAO = INSTANCE.accountDAO();
                                PrenotazioneDAO prenotazioneDAO = INSTANCE.prenotazioneDAO();

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
                                medicoDAO.addMedico(m);

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
                                pazienteDAO.addPaziente(p);

                                CartellaClinica c = new CartellaClinica(
                                        1,       // id
                                        5,       // numero referti
                                        10,      // spazio disponibile
                                        1,     // paziente_id
                                        1      // medico_id
                                );
                                cartellaClinicaDAO.addCartellaClinica(c);

                                Referto r = new Referto(1,"Esame sangue","Esame sangue 11/12/2024","EsameSangue.pdf",1);
                                cartellaClinicaDAO.addReferto(r);
                            });
                        }
                    })
                    //.createFromAsset(DATABASE_NAME)
                    .build();
        }

        return INSTANCE;
    }

    public final ExecutorService getOperationExecutor() {
        return operationExecutor;
    }

    public abstract CartellaClinicaDAO cartellaClinicaDAO();
    public abstract MedicoDAO medicoDAO();
    public abstract PazienteDAO pazienteDAO();
    public abstract AccountDAO accountDAO();
    public abstract PrenotazioneDAO prenotazioneDAO();
}