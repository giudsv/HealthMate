package com.healthmate;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.healthmate.database.bean.Medico;
import com.healthmate.database.bean.Paziente;

import java.time.Instant;

@Dao
public interface AccountDAO {

    @Query("SELECT username FROM Paziente WHERE username = :username AND password = :password")
    String getPaziente(String username, String password);

    @Query("SELECT username FROM Medico WHERE username = :username AND password = :password")
    String getMedico(String username, String password);

    @Query("INSERT INTO Medico (nome, cognome, CF, data_nascita, cellulare, email, username, password, studio, numero_albo) " +
            "VALUES (:nome, :cognome, :cf, :dataNascita, :cellulare, :email, :username, :password, :studio, :numeroAlbo)")
    void insertMedico(String nome, String cognome, String cf, Instant dataNascita,
                      String cellulare, String email, String username, String password,
                      String studio, String numeroAlbo);

    @Query("INSERT INTO Paziente (nome, cognome, CF, data_nascita, cellulare, email, username, password, sesso, email_tutore) " +
            "VALUES (:nome, :cognome, :cf, :dataNascita, :cellulare, :email, :username, :password, :sesso, :emailTutore)")
    void insertPaziente(String nome, String cognome, String cf, Instant dataNascita,
                        String cellulare, String email, String username, String password,
                        String sesso, String emailTutore);
}


