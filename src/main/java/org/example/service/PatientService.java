package org.example.service;

import org.example.model.Doctor;
import org.example.model.Patient;
import org.example.model.User;
import org.example.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService implements TableService<Patient>{

    private PatientRepository patientRepository;

    @Autowired
    PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    @Override
    public void createEntity(Patient patient) {
        patientRepository.save(patient);
    }

    @Override
    public List<Patient> readAllEntity() {
        return patientRepository.findAll();
    }

    @Override
    public Patient readOneEntity(long id) {
        return patientRepository.getById(id);
    }

    @Override
    public boolean updateEntity(Patient patient, long id) {
        patient.setId(id);
        patientRepository.save(patient);
        return true;
    }

    @Override
    public boolean deleteEntity(long id) {
        patientRepository.deleteById(id);
        return true;
    }

    public Doctor getDoctorByPatient (long id){
        return patientRepository.getById(id).getDoctor();
    }

    public Patient findByUser(long id){
        return patientRepository.findByUserId(id);
    }
}
