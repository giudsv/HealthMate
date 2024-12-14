package com.healthmate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import com.healthmate.database.bean.Referto;
import com.healthmate.database.bean.CartellaClinica;
import com.healthmate.database.dao.CartellaClinicaDAO;

public class CartellaClinicaUnitTest {

    private CartellaClinicaDAO mockCartellaClinicaDAO;

    @Before
    public void setup() {
        mockCartellaClinicaDAO = Mockito.mock(CartellaClinicaDAO.class);
    }

    // UC_04_TC_01 - Inserimento referto nella cartella clinica
    @Test
    public void testInserimentoRefertoInCartellaClinica() {
        CartellaClinica cartella = new CartellaClinica(6, 2, 100, 1, 1);
        Referto referto = new Referto(6, "Esame_Sangue", "Referto analisi del sangue", "esame.pdf", cartella.getId());

        doNothing().when(mockCartellaClinicaDAO).addReferto(referto);

        mockCartellaClinicaDAO.addReferto(referto);

        verify(mockCartellaClinicaDAO, times(1)).addReferto(referto);
        assertEquals("Esame_Sangue", referto.getNome());
        assertEquals("Referto analisi del sangue", referto.getDescrizione());
        assertEquals("esame.pdf", referto.getAllegato());
    }

    // UC_04_TC_02 - Inserimento referto dati vuoti
    @Test(expected = IllegalArgumentException.class)
    public void testInserimentoRefertoDatiVuoti() {
        Referto referto = new Referto(7, "", "Referto analisi", "esame.pdf", 1);

        doThrow(new IllegalArgumentException("Il campo Nome è obbligatorio.")).when(mockCartellaClinicaDAO).addReferto(referto);

        mockCartellaClinicaDAO.addReferto(referto);
    }

    // UC_04_TC_03 - Inserimento referto dati non validi
    @Test(expected = IllegalArgumentException.class)
    public void testInserimentoRefertoDatiNonValidi() {
        Referto referto = new Referto(8, "Esame_Sangue", "Referto analisi", "Allegato.exe", 1);

        doThrow(new IllegalArgumentException("Il formato dell’allegato non è corretto. Inserire un file in formato PDF o immagine.")).when(mockCartellaClinicaDAO).addReferto(referto);

        mockCartellaClinicaDAO.addReferto(referto);
    }

}
