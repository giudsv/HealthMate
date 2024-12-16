package com.healthmate.controller;

import com.healthmate.database.bean.CartellaClinica;
import com.healthmate.database.bean.Medico;
import com.healthmate.database.bean.Paziente;
import com.healthmate.database.bean.Referto;
import com.healthmate.database.dao.CartellaClinicaDAO;
import com.healthmate.database.dao.MedicoDAO;
import com.healthmate.database.dao.PazienteDAO;

import java.util.ArrayList;
import java.util.List;

public class RefertoController {
    private final CartellaClinicaDAO cartellaClinicaDAO;
    private final MedicoDAO medicoDAO;
    private final PazienteDAO pazienteDAO;

    public RefertoController(CartellaClinicaDAO cartellaClinicaDAO, MedicoDAO medicoDAO, PazienteDAO pazienteDAO) {
        this.cartellaClinicaDAO = cartellaClinicaDAO;
        this.medicoDAO = medicoDAO;
        this.pazienteDAO = pazienteDAO;
    }

    public List<Referto> showReferti(){
        List<Referto> referti = new ArrayList<>();
        referti = (List<Referto>) cartellaClinicaDAO.showReferti();
        return referti;
    }

    public void saveReferti(Referto r){
        cartellaClinicaDAO.addReferto(r);
    }

    public void deleteReferti(Referto r){
        cartellaClinicaDAO.deleteReferto(r);
    }

    public List<Referto> getAllRefertoByPazienteId(int pazienteId){
        List<Referto> refertiOfPaziente = new ArrayList<>();
        refertiOfPaziente = cartellaClinicaDAO.getAllByPazienteId(pazienteId);
        return refertiOfPaziente;
    }

    public List<Referto> getAllRefertoByMedicoId(int medicoId) {
        List<Referto> refertiOfMedico = new ArrayList<>();
        refertiOfMedico = cartellaClinicaDAO.getAllByMedicoId(medicoId);
        return refertiOfMedico;
    }

    public void saveCartellaClinica(CartellaClinica c){
        cartellaClinicaDAO.addCartellaClinica(c);
    }

    public void saveMedico(Medico m){
        medicoDAO.addMedico(m);
    }

    public void savePaziente(Paziente p){
        pazienteDAO.addPaziente(p);
    }

}

