package com.healthmate.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.healthmate.database.bean.Referto;

import java.util.List;

@Dao
public interface CartellaClinicaDAO {
    @Insert
    void insertAll(Referto...r);

    @Delete
    void delete(Referto referto);

    @Query("SELECT * FROM referto")
    List<Referto> getAll();
}
