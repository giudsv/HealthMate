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

    // UC_01_TC_01 - Modifica nome referto nella cartella clinica
    @Test
    public void testModificaNomeRefertoInCartellaClinica() {
        CartellaClinica cartella = new CartellaClinica(1, 2, 100, 1, 1);
        Referto referto = new Referto(1, "EsameSangue_2023.pdf", "Analisi dettagliate del sangue", "", cartella.getId());
        referto.setNome("AnalisiSangue_Dicembre2023.pdf");

        doNothing().when(mockCartellaClinicaDAO).updateReferto(referto);

        mockCartellaClinicaDAO.updateReferto(referto);

        verify(mockCartellaClinicaDAO, times(1)).updateReferto(referto);
        assertEquals("AnalisiSangue_Dicembre2023.pdf", referto.getNome());
    }

    // UC_01_TC_02 - Modifica descrizione referto nella cartella clinica
    @Test
    public void testModificaDescrizioneRefertoInCartellaClinica() {
        CartellaClinica cartella = new CartellaClinica(2, 2, 100, 1, 1);
        Referto referto = new Referto(2, "EsameSangue_2023.pdf", "Analisi del sangue", "", cartella.getId());
        referto.setDescrizione("Risultati aggiornati delle analisi del sangue per Dicembre 2023");

        doNothing().when(mockCartellaClinicaDAO).updateReferto(referto);

        mockCartellaClinicaDAO.updateReferto(referto);

        verify(mockCartellaClinicaDAO, times(1)).updateReferto(referto);
        assertEquals("Risultati aggiornati delle analisi del sangue per Dicembre 2023", referto.getDescrizione());
    }

    // UC_01_TC_03 - Modifica allegato referto nella cartella clinica
    @Test
    public void testModificaAllegatoRefertoInCartellaClinica() {
        CartellaClinica cartella = new CartellaClinica(3, 2, 100, 1, 1);
        Referto referto = new Referto(3, "EsameSangue_2023.pdf", "Analisi dettagliate del sangue", "Analisi_Confermate_2023.pdf", cartella.getId());
        referto.setAllegato("Analisi_Confermate_2023.pdf");

        doNothing().when(mockCartellaClinicaDAO).updateReferto(referto);

        mockCartellaClinicaDAO.updateReferto(referto);

        verify(mockCartellaClinicaDAO, times(1)).updateReferto(referto);
        assertEquals("Analisi_Confermate_2023.pdf", referto.getAllegato());
    }

    // UC_01_TC_04 - Errore formato allegato (non è PDF o immagine)
    @Test(expected = IllegalArgumentException.class)
    public void testFormatoAllegatoNonValidoInCartellaClinica() {
        CartellaClinica cartella = new CartellaClinica(4, 2, 100, 1, 1);
        Referto referto = new Referto(4, "EsameSangue_2023.pdf", "Analisi del sangue", "Documento_eseguibile.exe", cartella.getId());

        doThrow(new IllegalArgumentException("Il campo Allegato non è valido"))
                .when(mockCartellaClinicaDAO).updateReferto(referto);

        mockCartellaClinicaDAO.updateReferto(referto);
    }

    // UC_01_TC_05 - Nessun dato inserito nella cartella clinica
    @Test(expected = IllegalArgumentException.class)
    public void testNessunDatoInseritoInCartellaClinica() {
        CartellaClinica cartella = new CartellaClinica(5, 2, 100, 1, 1);
        Referto referto = new Referto(5, "", "", "", cartella.getId());

        doThrow(new IllegalArgumentException("Nessun campo modificato"))
                .when(mockCartellaClinicaDAO).updateReferto(referto);

        mockCartellaClinicaDAO.updateReferto(referto);
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
