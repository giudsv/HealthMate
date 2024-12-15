package com.healthmate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import com.healthmate.database.bean.Visita;
import com.healthmate.database.dao.PrenotazioneDAO;

import static org.junit.Assert.*;

import java.time.Instant;

public class PrenotazioneUnitTest {

    private PrenotazioneDAO mockPrenotazioneDAO;

    @Before
    public void setup() {
        mockPrenotazioneDAO = Mockito.mock(PrenotazioneDAO.class);
    }

    // TCS_UC_05: Prenotazione Visita

    @Test
    public void test_UC_05_TC_01_inserimentoDatiNonValidi() {
        Instant dataNonValida = null;
        Visita visita = new Visita(1, dataNonValida, "Studio A", 1, 1);

        doThrow(new IllegalArgumentException("Il formato della data non è valido"))
                .when(mockPrenotazioneDAO).insertPrenotazione(visita);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mockPrenotazioneDAO.insertPrenotazione(visita);
        });

        assertEquals("Il formato della data non è valido", exception.getMessage());
        verify(mockPrenotazioneDAO, times(1)).insertPrenotazione(visita);
        verifyNoMoreInteractions(mockPrenotazioneDAO);
    }

    @Test
    public void test_UC_05_TC_02_inserimentoDatiVuoti() {
        Visita visita = new Visita(2, null, "Studio A", 1, 1);

        doThrow(new IllegalArgumentException("La data è obbligatoria"))
                .when(mockPrenotazioneDAO).insertPrenotazione(visita);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mockPrenotazioneDAO.insertPrenotazione(visita);
        });

        assertEquals("La data è obbligatoria", exception.getMessage());
        verify(mockPrenotazioneDAO, times(1)).insertPrenotazione(visita);
        verifyNoMoreInteractions(mockPrenotazioneDAO);
    }

    @Test
    public void test_UC_05_TC_03_inserimentoDatiValidi() {
        Instant dataValida = Instant.parse("2024-12-31T11:30:00Z");
        Visita visita = new Visita(3, dataValida, "Studio A", 1, 1);

        doNothing().when(mockPrenotazioneDAO).insertPrenotazione(visita);
        when(mockPrenotazioneDAO.queryVisitaPaziente(1)).thenReturn(visita);

        mockPrenotazioneDAO.insertPrenotazione(visita);

        Visita prenotazioneRecuperata = mockPrenotazioneDAO.queryVisitaPaziente(1);

        assertNotNull(prenotazioneRecuperata);
        assertEquals(dataValida, prenotazioneRecuperata.getData());
        assertEquals("Studio A", prenotazioneRecuperata.getLuogo());

        verify(mockPrenotazioneDAO, times(1)).insertPrenotazione(visita);
        verify(mockPrenotazioneDAO, times(1)).queryVisitaPaziente(1);
        verifyNoMoreInteractions(mockPrenotazioneDAO);
    }
}

