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

    public List<Referto> getAllReferti(){
        List<Referto> referti = new ArrayList<>();
        referti = cartellaClinicaDAO.getAll();
        return referti;
    }

    public void saveReferti(Referto r){
        cartellaClinicaDAO.insertAll(r);
    }

    public void deleteReferti(Referto r){
        cartellaClinicaDAO.delete(r);
    }
}
