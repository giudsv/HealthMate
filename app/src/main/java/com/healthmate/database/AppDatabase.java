package com.healthmate.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.healthmate.database.bean.Medico;
import com.healthmate.database.bean.Paziente;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TypeConverters(value = {Converters.class})
@Database (
        version = 1,
        entities = {
                Medico.class,
                Paziente.class
        }
)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE = null;
    private static final String DATABASE_NAME = "db-1.0.0";
    private final ExecutorService operationExecutor = Executors.newSingleThreadExecutor();

    public static synchronized AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                    //.createFromAsset(DATABASE_NAME)
                    .build();
        }

        return INSTANCE;
    }

    public final ExecutorService getOperationExecutor() {
        return operationExecutor;
    }
}