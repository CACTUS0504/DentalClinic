package org.example.controller;

import org.example.model.Appointment;
import org.example.model.Doctor;
import org.example.service.AppointmentService;
import org.example.service.PatientService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/api")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final PatientService patientService;
    private final UserService userService;

    @Autowired
    AppointmentController(AppointmentService appointmentService, PatientService patientService, UserService userService){
        this.appointmentService = appointmentService;
        this.patientService = patientService;
        this.userService = userService;
    }

    // Добавить view
    @GetMapping(value="")
    @ResponseBody
    public List<Appointment> readAll(Model model) {
        model.addAttribute("appointments", appointmentService.readAllEntity());
        return appointmentService.readAllEntity();
    }

    @GetMapping(value = "patients/appointment")
    public String returnAppointment(Model model) {
        // Передаём строку с текущей датой, чтобы нельзя было записаться на прошедшие дни
        String currentDate = LocalDate.now().plusDays(1).toString();
        model.addAttribute("currentDate", currentDate);
        Doctor doctor = patientService.findByUser(userService.getCurrentUser().getId()).getDoctor();
        model.addAttribute("doctor", doctor);
        model.addAttribute("currentUser", userService.getCurrentUser());
        if (doctor == null) return "/patient/no_doctor";
        return "/patient/appointment";
    }

    @PostMapping(value = "patients/appointment")
    public String createAppointment(@RequestParam("time") String time, @RequestParam("date") String date, Model model)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateInString = date + " " + time + ":00";
        Date date_formatted = formatter.parse(dateInString);

        Doctor doctor = patientService.findByUser(userService.getCurrentUser().getId()).getDoctor();
        List<Appointment> appointments = doctor.getAppointments();
        boolean date_ok = true;
        if (appointments != null){
            for (Appointment appointment : appointments) {
                if (appointment.getAppointment_date().compareTo(date_formatted) == 0) {
                    model.addAttribute("message", "Это время уже занято, попробуйте другое");
                    date_ok = false;
                    break;
                }
            }
        }
        if (date_ok){
            Appointment appointment = new Appointment();
            appointment.setPatient(patientService.findByUser(userService.getCurrentUser().getId()));
            appointment.setTitle("Описание записи");
            appointment.setDoctor(doctor);
            appointment.setAppointment_date(date_formatted);
            appointmentService.createEntity(appointment);
            model.addAttribute("message", "Вы были успешно записаны к врачу");
        }

        // Передаём строку с текущей датой + 1 день, чтобы нельзя было записаться на прошедшие дни
        String currentDate = LocalDate.now().plusDays(1).toString();
        model.addAttribute("currentDate", currentDate);
        model.addAttribute("currentUser", userService.getCurrentUser());
        model.addAttribute("doctor", doctor );

        return "/patient/appointment";
    }
}