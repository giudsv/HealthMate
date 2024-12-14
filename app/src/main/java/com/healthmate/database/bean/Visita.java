package com.healthmate.database.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.Instant;
import java.util.Objects;

@Entity (
        tableName = "Visita",
        foreignKeys = {
                @ForeignKey(
                        entity = Paziente.class,
                        parentColumns = "id",
                        childColumns = "paziente_id",
                        onUpdate = ForeignKey.CASCADE,
                        onDelete = ForeignKey.SET_NULL
                ),
                @ForeignKey(
                        entity = Medico.class,
                        parentColumns = "id",
                        childColumns = "medico_id",
                        onUpdate = ForeignKey.CASCADE,
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class Visita {
    @PrimaryKey @ColumnInfo(name = "id")
    private final int id;

    @ColumnInfo(name = "data")
    private final Instant data;

    @ColumnInfo(name = "luogo")
    private final String luogo;

    @ColumnInfo(name = "paziente_id", index = true)
    private final int pazienteId;

    @ColumnInfo(name = "medico_id", index = true)
    private final int medico_id;

    public Visita(int id, Instant data, String luogo, int pazienteId, int medico_id) {
        this.id = id;
        this.data = data;
        this.luogo = luogo;
        this.pazienteId = pazienteId;
        this.medico_id = medico_id;
    }

    public int getId() {
        return id;
    }

    public Instant getData() {
        return data;
    }

    public String getLuogo() {
        return luogo;
    }

    public int getPazienteId() {
        return pazienteId;
    }

    public int getMedico_id() {
        return medico_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Visita)) return false;
        Visita visita = (Visita) o;
        return getId() == visita.getId() && getPazienteId() == visita.getPazienteId() &&
                getMedico_id() == visita.getMedico_id() &&
                Objects.equals(getData(), visita.getData()) &&
                Objects.equals(getLuogo(), visita.getLuogo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getData(), getLuogo(), getPazienteId(), getMedico_id());
    }
}
