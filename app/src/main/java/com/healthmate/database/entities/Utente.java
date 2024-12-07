package com.healthmate.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import java.time.Instant;
import java.util.Objects;

public abstract class Utente {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "nome")
    private String nome;

    @ColumnInfo(name = "cognome")
    private String cognome;

    @ColumnInfo(name = "CF")
    private String cf;

    @ColumnInfo(name = "data_nascita")
    private Instant dataNascita;

    @ColumnInfo(name = "cellulare")
    private String cellulare;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "password")
    private String password;

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

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public Instant getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Instant dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getCellulare() {
        return cellulare;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", cf='" + cf + '\'' +
                ", dataNascita=" + dataNascita +
                ", cellulare='" + cellulare + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utente utente = (Utente) o;
        return id == utente.id && Objects.equals(nome, utente.nome) && Objects.equals(cognome, utente.cognome) && Objects.equals(cf, utente.cf) && Objects.equals(dataNascita, utente.dataNascita) && Objects.equals(cellulare, utente.cellulare) && Objects.equals(email, utente.email) && Objects.equals(username, utente.username) && Objects.equals(password, utente.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, cognome, cf, dataNascita, cellulare, email, username, password);
    }
}
