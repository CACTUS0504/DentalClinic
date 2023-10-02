package org.example.service;

import org.example.model.Appointment;
import org.example.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService implements TableService<Appointment>{

    private final AppointmentRepository appointmentRepository;

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
        Optional<Appointment> optAppointment = appointmentRepository.findById(id);
        if (optAppointment.isPresent()) return optAppointment.get();
        else throw new IllegalArgumentException("Appointment with id = " + id + " doesn't exist");
    }

    @Override
    public boolean updateEntity(Appointment appointment, long id) {
        appointment.setId(id);
        appointmentRepository.save(appointment);
        return true;
    }

    @Override
    public boolean deleteEntity(long id) {
        if (appointmentRepository.findById(id).isPresent()){
            appointmentRepository.deleteById(id);
            return true;
        } else return false;
    }
}
