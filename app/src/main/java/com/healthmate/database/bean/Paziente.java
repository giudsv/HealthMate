package com.healthmate.database.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.time.Instant;
import java.util.Objects;

@Entity(
        foreignKeys = {
                @ForeignKey(
                        entity = Medico.class,
                        parentColumns = "id",
                        childColumns = "medico_id",
                        onDelete = ForeignKey.SET_NULL,
                        onUpdate = ForeignKey.CASCADE
                )
        }
)
public class Paziente extends Utente{
    @ColumnInfo(name = "sesso")
    private String sesso;

    @ColumnInfo(name = "email_tutore")
    private String emailTutore;

    @ColumnInfo(name = "medico_id", index = true)
    private int medicoId;

    public Paziente(){}

    public Paziente(int id, String nome, String cognome, String cf, Instant dataNascita, String cellulare, String email, String username, String password, String sesso, String emailTutore, int medicoId) {
        super(id, nome, cognome, cf, dataNascita, cellulare, email, username, password);
        this.sesso = sesso;
        this.emailTutore = emailTutore;
        this.medicoId = medicoId;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public String getEmailTutore() {
        return emailTutore;
    }

    public void setEmailTutore(String emailTutore) {
        this.emailTutore = emailTutore;
    }

    public int getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(int medicoId) {
        this.medicoId = medicoId;
    }

    @Override
    public String toString() {
        return "Paziente{" +
                "sesso='" + sesso + '\'' +
                ", emailTutore='" + emailTutore + '\'' +
                ", medicoId=" + medicoId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paziente paziente = (Paziente) o;
        return medicoId == paziente.medicoId && Objects.equals(sesso, paziente.sesso) && Objects.equals(emailTutore, paziente.emailTutore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sesso, emailTutore, medicoId);
    }
}
