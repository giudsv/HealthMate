package com.healthmate.database.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.util.Objects;

@Entity
public class Referto {
    @ColumnInfo(name = "nome")
    private String nome;

    @ColumnInfo(name = "descrizione")
    private String descrizione;

    @ColumnInfo(name = "allegato")
    private String allegato;

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
        return "RefertoBean{" +
                "nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", allegato='" + allegato + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefertoBean RefertoBean = (RefertoBean) o;
        return Objects.equals(nome, RefertoBean.nome) && Objects.equals(descrizione, RefertoBean.descrizione) && Objects.equals(allegato, RefertoBean.allegato);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, descrizione, allegato);
    }
}
