package org.example.service;

import org.example.model.Doctor;
import org.example.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService implements TableService<Doctor>{

    private final DoctorRepository doctorRepository;

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
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        if (optionalDoctor.isPresent()) return optionalDoctor.get();
        else throw new IllegalArgumentException("Doctor with id = " + id + " doesn't exist");
    }

    @Override
    public boolean updateEntity(Doctor doctor, long id) {
        doctor.setId(id);
        doctorRepository.save(doctor);
        return true;
    }

    @Override
    public boolean deleteEntity(long id) {
        if (doctorRepository.findById(id).isPresent()){
            doctorRepository.deleteById(id);
            return true;
        } else return false;
    }

    public Doctor findByUser(long id){
        return doctorRepository.findByUserId(id);
    }
}
