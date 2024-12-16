package com.healthmate.database.bean;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.healthmate.database.dao.CartellaClinicaDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public CartellaClinica(){}

    public CartellaClinica(int id, int numReferti, int spazioDisponibile, int paziente_id, int medico_id) {
        this.id = id;
        this.numReferti = numReferti;
        this.spazioDisponibile = spazioDisponibile;
        this.paziente_id = paziente_id;
        this.medico_id = medico_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumReferti() {
        return numReferti;
    }

    public void setNumReferti(int numReferti) {
        this.numReferti = numReferti;
    }

    public int getSpazioDisponibile() {
        return spazioDisponibile;
    }

    public void setSpazioDisponibile(int spazioDisponibile) {
        this.spazioDisponibile = spazioDisponibile;
    }

    public int getPaziente_id() {
        return paziente_id;
    }

    public void setPaziente_id(int paziente_id) {
        this.paziente_id = paziente_id;
    }

    public int getMedico_id() {
        return medico_id;
    }

    public void setMedico_id(int medico_id) {
        this.medico_id = medico_id;
    }

    @Override
    public String toString() {
        return "CartellaClinica{" +
                "id=" + id +
                ", numReferti=" + numReferti +
                ", spazioDisponibile=" + spazioDisponibile +
                ", paziente_id=" + paziente_id +
                ", medico_id=" + medico_id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartellaClinica that = (CartellaClinica) o;
        return id == that.id && numReferti == that.numReferti && spazioDisponibile == that.spazioDisponibile && paziente_id == that.paziente_id && medico_id == that.medico_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numReferti, spazioDisponibile, paziente_id, medico_id);
    }
}

