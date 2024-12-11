package com.healthmate;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.healthmate.database.bean.Medico;
import com.healthmate.database.bean.Paziente;

@Dao
public interface AccountDAO {

    @Insert
    void insertPaziente(Paziente paziente);

    @Insert
    void insertMedico(Medico medico);

    @Query("SELECT * FROM Paziente WHERE username = :username AND password = :password")
    Paziente getPaziente(String username, String password);

    @Query("SELECT * FROM Medico WHERE username = :username AND password = :password")
    Medico getMedico(String username, String password);

}
