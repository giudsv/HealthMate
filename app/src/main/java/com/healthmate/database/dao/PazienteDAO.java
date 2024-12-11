package com.healthmate.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.healthmate.database.bean.CartellaClinica;
import com.healthmate.database.bean.Paziente;
import com.healthmate.database.bean.Referto;

import java.util.List;

@Dao
public interface PazienteDAO {
    @Insert
    void addPaziente(Paziente...p);
}
