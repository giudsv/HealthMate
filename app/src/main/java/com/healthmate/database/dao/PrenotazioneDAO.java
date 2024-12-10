package com.healthmate.database.dao;

import androidx.annotation.Nullable;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.healthmate.database.bean.Visita;

import java.util.List;

@Dao
public interface PrenotazioneDAO {
    /* Query */

    /**
     * Restituisce la Visita prenotata dal Paziente.
     * @param pazienteID l'{@code id} del Paziente
     */
    @Nullable
    @Query("SELECT * FROM Visita WHERE paziente_id = :pazienteID")
    Visita queryVisitaPaziente(int pazienteID);

    /**
     * Restituisce le Visite prenotate dal Medico.
     * @param medicoID l'{@code id} del Paziente
     */
    @Query("SELECT * FROM Visita WHERE medico_id = :medicoID")
    List<Visita> queryVisiteMedico(int medicoID);

    /* Operazioni di Aggiornamento */
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
