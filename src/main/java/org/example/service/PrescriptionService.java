package org.example.service;

import org.example.model.Patient;
import org.example.model.Prescription;
import org.example.repository.PatientRepository;
import org.example.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionService implements TableService<Prescription>{

    private PrescriptionRepository prescriptionRepository;

    @Autowired
    PrescriptionService(PrescriptionRepository prescriptionRepository){
        this.prescriptionRepository = prescriptionRepository;
    }

    @Override
    public void createEntity(Prescription prescription) {
        prescriptionRepository.save(prescription);
    }

    @Override
    public List<Prescription> readAllEntity() {
        return prescriptionRepository.findAll();
    }

    @Override
    public Prescription readOneEntity(long id) {
        return prescriptionRepository.getById(id);
    }

    @Override
    public boolean updateEntity(Prescription prescription, long id) {
        prescription.setId(id);
        prescriptionRepository.save(prescription);
        return true;
    }

    @Override
    public boolean deleteEntity(long id) {
        prescriptionRepository.deleteById(id);
        return true;
    }
}