package org.example.service;

import org.example.model.Appointment;
import org.example.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService implements TableService<Appointment>{

    private AppointmentRepository appointmentRepository;

    @Autowired
    AppointmentService(AppointmentRepository appointmentRepository){
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public void createEntity(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> readAllEntity() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment readOneEntity(long id) {
        return appointmentRepository.getById(id);
    }

    @Override
    public boolean updateEntity(Appointment appointment, long id) {
        appointment.setId(id);
        appointmentRepository.save(appointment);
        return true;
    }

    @Override
    public boolean deleteEntity(long id) {
        appointmentRepository.deleteById(id);
        return true;
    }
}
