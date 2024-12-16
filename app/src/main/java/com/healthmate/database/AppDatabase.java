package com.healthmate.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.healthmate.database.bean.CartellaClinica;
import com.healthmate.database.bean.Medico;
import com.healthmate.database.bean.Paziente;
import com.healthmate.database.bean.Referto;
import com.healthmate.database.bean.Visita;

import com.healthmate.database.dao.AccountDAO;
import com.healthmate.database.dao.CartellaClinicaDAO;
import com.healthmate.database.dao.PrenotazioneDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TypeConverters(value = {Converters.class})
@Database (
        version = 1,
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
                    //.createFromAsset("db-1.1.0.db3")
                    .build();
        }

        return INSTANCE;
    }

    public final ExecutorService getOperationExecutor() {
        return operationExecutor;
    }

    // DAO
    public abstract PrenotazioneDAO prenotazioneDAO();
    public abstract CartellaClinicaDAO cartellaClinicaDAO();
    public abstract AccountDAO accountDAO();
}