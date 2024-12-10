package com.healthmate.database.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(
        foreignKeys = {
                @ForeignKey(
                        entity = CartellaClinica.class,
                        parentColumns = "id",
                        childColumns = "cartellaclinica_id",
                        onDelete = ForeignKey.SET_NULL,
                        onUpdate = ForeignKey.CASCADE
                )
        }
)
public class Referto {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "nome")
    private String nome;

    @ColumnInfo(name = "descrizione")
    private String descrizione;

    @ColumnInfo(name = "allegato")
    private String allegato;

    @ColumnInfo(name = "cartellaclinica_id")
    private int cartellaclinica_id;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getAllegato() {
        return allegato;
    }

    public void setAllegato(String allegato) {
        this.allegato = allegato;
    }

    @Override
    public String toString() {
        return "Referto{" +
                "nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", allegato='" + allegato + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Referto Referto = (Referto) o;
        return Objects.equals(nome, Referto.nome) && Objects.equals(descrizione, Referto.descrizione) && Objects.equals(allegato, Referto.allegato);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, descrizione, allegato);
    }
}
