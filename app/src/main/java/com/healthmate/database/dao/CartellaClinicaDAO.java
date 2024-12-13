package com.healthmate.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.healthmate.database.bean.CartellaClinica;
import com.healthmate.database.bean.Referto;

import java.util.List;

@Dao
public interface CartellaClinicaDAO {
    @Insert
    void addReferto(Referto...r);

    @Delete
    void deleteReferto(Referto referto);

    @Update
    void updateReferto(Referto referto);

    @Query("SELECT * FROM referto")
    List<Referto> showReferti();

    @Query("SELECT r.id, r.nome, r.descrizione, r.allegato, r.cartellaclinica_id " +
            "FROM REFERTO r Inner join CartellaClinica c ON " +
            "c.id = r.cartellaclinica_id " +
            "WHERE c.paziente_id = :pazienteId")
    List<Referto> getAllByPazienteId(int pazienteId);

    @Query("SELECT r.id, r.nome, r.descrizione, r.allegato, r.cartellaclinica_id " +
            "FROM REFERTO r Inner join CartellaClinica c ON " +
            " c.id = r.cartellaclinica_id " +
            " WHERE c.medico_id = :medicoId ")
    List<Referto> getAllByMedicoId(int medicoId);

    @Insert
    void addCartellaClinica(CartellaClinica...c);
}
