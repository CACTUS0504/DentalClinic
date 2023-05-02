package org.example.service;

import org.example.model.Doctor;
import org.example.repository.DoctorRepository;
import org.example.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;

@Service
public class DoctorService implements TableService<Doctor>{

    private DoctorRepository doctorRepository;

    @Autowired
    DoctorService(DoctorRepository doctorRepository){
        this.doctorRepository = doctorRepository;
    }

    @Override
    public void createEntity(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    @Override
    public List<Doctor> readAllEntity() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor readOneEntity(long id) {
        return doctorRepository.getById(id);
    }

    @Override
    public boolean updateEntity(Doctor doctor, long id) {
        doctor.setId(id);
        doctorRepository.save(doctor);
        return true;
    }

    @Override
    public boolean deleteEntity(long id) {
        doctorRepository.deleteById(id);
        return true;
    }
}
