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

    public Referto(){}

    public Referto(int id, String nome, String descrizione, String allegato, int cartellaclinica_id) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.allegato = allegato;
        this.cartellaclinica_id = cartellaclinica_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getCartellaclinica_id() {
        return cartellaclinica_id;
    }

    public void setCartellaclinica_id(int cartellaclinica_id) {
        this.cartellaclinica_id = cartellaclinica_id;
    }

    @Override
    public String toString() {
        return "Referto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", allegato='" + allegato + '\'' +
                ", cartellaclinica_id=" + cartellaclinica_id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Referto referto = (Referto) o;
        return id == referto.id && cartellaclinica_id == referto.cartellaclinica_id && Objects.equals(nome, referto.nome) && Objects.equals(descrizione, referto.descrizione) && Objects.equals(allegato, referto.allegato);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, descrizione, allegato, cartellaclinica_id);
    }
}
