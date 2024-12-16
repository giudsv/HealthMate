package com.healthmate.database.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.time.Instant;
import java.util.Objects;

@Entity
public class Medico extends Utente{
    @ColumnInfo(name = "studio")
    public String studio;

    @ColumnInfo(name = "numero_albo")
    public String numeroAlbo;

    public Medico(){}

    public Medico(int id, String nome, String cognome, String cf, Instant dataNascita, String cellulare, String email, String username, String password, String studio, String numeroAlbo) {
        super(id, nome, cognome, cf, dataNascita, cellulare, email, username, password);
        this.studio = studio;
        this.numeroAlbo = numeroAlbo;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getNumeroAlbo() {
        return numeroAlbo;
    }

    public void setNumeroAlbo(String numeroAlbo) {
        this.numeroAlbo = numeroAlbo;
    }

    @Override
    public String toString() {
        return "Medico{" +
                "studio='" + studio + '\'' +
                ", numeroAlbo='" + numeroAlbo + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Medico medico = (Medico) o;
        return Objects.equals(studio, medico.studio) && Objects.equals(numeroAlbo, medico.numeroAlbo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), studio, numeroAlbo);
    }
}
