package com.healthmate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import com.healthmate.database.bean.Paziente;
import com.healthmate.AccountDAO;

import java.time.Instant;

public class AccountUnitTest {

    private AccountDAO mockAccountDAO;

    @Before
    public void setup() {
        mockAccountDAO = Mockito.mock(AccountDAO.class);
    }

    // TCS_UC_02: Login

    @Test
    public void testLoginCredenzialiValide() {
        Paziente paziente = new Paziente();
        paziente.setUsername("mariorossi");
        paziente.setPassword("Passwordcorretta");

        when(mockAccountDAO.getPaziente("mariorossi", "Passwordcorretta")).thenReturn(true);

        boolean result = mockAccountDAO.getPaziente("mariorossi", "Passwordcorretta");

        assertTrue(result);
    }

    @Test
    public void testLoginCredenzialiNonValide() {
        when(mockAccountDAO.getPaziente("marirossi", "Passworderrata")).thenReturn(false);

        boolean result = mockAccountDAO.getPaziente("marirossi", "Passworderrata");

        assertFalse(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLoginCredenzialiVuote() {
        doThrow(new IllegalArgumentException("I campi Username e Password sono obbligatori.")).when(mockAccountDAO).getPaziente("", "");

        mockAccountDAO.getPaziente("", "");
    }

    // TCS_UC_03: Registrazione

    @Test(expected = IllegalArgumentException.class)
    public void testRegistrazioneCampiVuoti() {
        doThrow(new IllegalArgumentException("L’username è obbligatorio.")).when(mockAccountDAO).insertPaziente(
                "", "", "", Instant.EPOCH, "", "", "", "", "", ""
        );

        mockAccountDAO.insertPaziente("", "", "", Instant.EPOCH, "", "", "", "", "", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegistrazioneDatiNonValidi() {
        Instant fixedInstant = Instant.parse("2024-12-13T15:01:00.537944200Z");
        doThrow(new IllegalArgumentException("Il Codice Fiscale deve essere composto da 16 caratteri.")).when(mockAccountDAO).insertPaziente(
                "Mario", "Rossi", "RSSMRA85T10H501", fixedInstant, "1234567890", "mario.rossi@email.com", "mariorossi", "Password123", "M", ""
        );

        mockAccountDAO.insertPaziente("Mario", "Rossi", "RSSMRA85T10H501", fixedInstant, "1234567890", "mario.rossi@email.com", "mariorossi", "Password123", "M", "");
    }

    @Test
    public void testRegistrazioneDatiValidi() {
        Instant fixedInstant = Instant.parse("2024-12-13T15:01:00.537944200Z");

        doNothing().when(mockAccountDAO).insertPaziente(
                "Mario", "Rossi", "RSSMRA85T10H501Z", fixedInstant, "1234567890", "mario.rossi@email.com", "mariorossi", "Password123", "M", ""
        );

        mockAccountDAO.insertPaziente("Mario", "Rossi", "RSSMRA85T10H501Z", fixedInstant, "1234567890", "mario.rossi@email.com", "mariorossi", "Password123", "M", "");

        verify(mockAccountDAO, times(1)).insertPaziente(
                "Mario", "Rossi", "RSSMRA85T10H501Z", fixedInstant, "1234567890", "mario.rossi@email.com", "mariorossi", "Password123", "M", ""
        );
    }
}