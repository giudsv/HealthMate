package com.healthmate.database.bean;
import com.healthmate.database.dao.CartellaClinicaDAO;

import java.util.ArrayList;
import java.util.List;

public class CartellaClinica {
    private final CartellaClinicaDAO cartellaClinicaDAO;

    public CartellaClinica(CartellaClinicaDAO cartellaClinicaDAO){
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
