package com.healthmate.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;

import com.healthmate.database.bean.Visita;

@Dao
public interface PrenotazioneDAO {
    /**
     * Salva la visita specificata.
     */
    @Insert
    void insertPrenotazione(Visita visita);

    /**
     * Modifica la visita specificata.
     */
    @Update
    void updatePrenotazione(Visita visita);
}
