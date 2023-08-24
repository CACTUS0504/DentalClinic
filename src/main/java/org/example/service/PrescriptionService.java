package org.example.service;

import org.example.model.Prescription;
import org.example.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionService implements TableService<Prescription>{

    private final PrescriptionRepository prescriptionRepository;

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
        Optional<Prescription> prescriptionOptional = prescriptionRepository.findById(id);
        if (prescriptionOptional.isPresent()) return prescriptionOptional.get();
        else throw new IllegalArgumentException("Prescription with id = " + id + " doesn't exist");
    }

    @Override
    public boolean updateEntity(Prescription prescription, long id) {
        prescription.setId(id);
        prescriptionRepository.save(prescription);
        return true;
    }

    @Override
    public boolean deleteEntity(long id) {
        if (prescriptionRepository.findById(id).isPresent()){
            prescriptionRepository.deleteById(id);
            return true;
        } else return false;
    }
}