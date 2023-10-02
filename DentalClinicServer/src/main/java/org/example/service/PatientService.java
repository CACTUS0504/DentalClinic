package org.example.service;

import org.example.model.Doctor;
import org.example.model.Patient;
import org.example.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService implements TableService<Patient>{

    private final PatientRepository patientRepository;

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
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isPresent()) return optionalPatient.get();
        else throw new IllegalArgumentException("Patient with id = " + id + " doesn't exist");
    }

    @Override
    public boolean updateEntity(Patient patient, long id) {
        patient.setId(id);
        patientRepository.save(patient);
        return true;
    }

    @Override
    public boolean deleteEntity(long id) {
        if (patientRepository.findById(id).isPresent()){
            patientRepository.deleteById(id);
            return true;
        } else return false;
    }

    public Doctor getDoctorByPatient (long id){
        return patientRepository.getById(id).getDoctor();
    }

    public Patient findByUser(long id){
        return patientRepository.findByUserId(id);
    }
}
