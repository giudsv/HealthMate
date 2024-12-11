package com.healthmate.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.healthmate.database.bean.CartellaClinica;
import com.healthmate.database.bean.Medico;
import com.healthmate.database.bean.Referto;

import java.util.List;

@Dao
public interface MedicoDAO {
    @Insert
    void addMedico(Medico...m);
}
