package com.healthmate.controller;

import com.healthmate.database.bean.Referto;
import com.healthmate.database.dao.CartellaClinicaDAO;

import java.util.ArrayList;
import java.util.List;

public class RefertoController {
    private final CartellaClinicaDAO cartellaClinicaDAO;

    public RefertoController(CartellaClinicaDAO cartellaClinicaDAO){
        this.cartellaClinicaDAO = cartellaClinicaDAO;
    }

    public List<Referto> showReferti(){
        List<Referto> referti = new ArrayList<>();
        referti = cartellaClinicaDAO.showReferti();
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
}
