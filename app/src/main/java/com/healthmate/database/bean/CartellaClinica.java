package com.healthmate.database.bean;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.healthmate.database.dao.CartellaClinicaDAO;

import java.util.ArrayList;
import java.util.List;

@Entity(
        foreignKeys = {
                @ForeignKey(
                        entity = Paziente.class,
                        parentColumns = "id",
                        childColumns = "paziente_id",
                        onDelete = ForeignKey.SET_NULL,
                        onUpdate = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Medico.class,
                        parentColumns = "id",
                        childColumns = "medico_id",
                        onDelete = ForeignKey.SET_NULL,
                        onUpdate = ForeignKey.CASCADE
                )
        }
)
public class CartellaClinica {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "numReferti")
    private int numReferti;

    @ColumnInfo(name = "spazioDisponibile")
    private int spazioDisponibile;

    @ColumnInfo(name = "paziente_id")
    private int paziente_id;

    @ColumnInfo(name = "medico_id")
    private int medico_id;



    private final CartellaClinicaDAO cartellaClinicaDAO;

    public CartellaClinica(CartellaClinicaDAO cartellaClinicaDAO){
        this.cartellaClinicaDAO = cartellaClinicaDAO;
    }

    public List<Referto> getAllReferti(){
        List<Referto> referti = new ArrayList<>();
        referti = cartellaClinicaDAO.getAll();
        return referti;
    }

    public void saveReferti(Referto r){
        cartellaClinicaDAO.insertAll(r);
    }

    public void deleteReferti(Referto r){
        cartellaClinicaDAO.delete(r);
    }
}
