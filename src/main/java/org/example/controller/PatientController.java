package org.example.controller;

import org.example.model.Appointment;
import org.example.model.Doctor;
import org.example.model.Patient;
import org.example.service.AppointmentService;
import org.example.service.DoctorService;
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
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService patientService;
    private final UserService userService;
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;

    @Autowired
    PatientController(PatientService patientService, UserService userService, DoctorService doctorService, AppointmentService appointmentService){
        this.patientService = patientService;
        this.userService = userService;
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
    }

    // Добавить view
    @GetMapping(value="")
    @ResponseBody
    public List<Patient> readAll(Model model) {
        model.addAttribute("patients", patientService.readAllEntity());
        return patientService.readAllEntity();
    }

    @GetMapping(value = "/cabinet")
    public String returnCabinet(Model model) {
        Patient patient = patientService.findByUser(userService.getCurrentUser().getId());
        model.addAttribute("currentUser", userService.getCurrentUser());
        model.addAttribute("currentPatient", patient);
        model.addAttribute("doctor", patient.getDoctor());
        return "/patient/cabinet";
    }
}
