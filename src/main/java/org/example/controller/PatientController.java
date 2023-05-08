package org.example.controller;

import org.example.model.Patient;
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
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService patientService;
    private final UserService userService;
    private final DoctorService doctorService;

    @Autowired
    PatientController(PatientService patientService, UserService userService, DoctorService doctorService){
        this.patientService = patientService;
        this.userService = userService;
        this.doctorService = doctorService;
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

    @GetMapping(value = "/appointment")
    public String returnAppointment(Model model) {
        // Передаём строку с текущей датой, чтобы нельзя было записаться на прошедшие дни
        String currentDate = LocalDate.now().toString();
        model.addAttribute("currentDate", currentDate);
        model.addAttribute("date", new Date());
        model.addAttribute("time", new String());
        return "/patient/appointment";
    }

    @PostMapping(value = "/appointment")
    public String createAppointment(@RequestParam("time") String time, @RequestParam("date") String date, Model model) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateInString = date + " " + time + ":00";
        Date date_formatted = formatter.parse(dateInString);
        System.out.println(date_formatted);
        return "/patient/appointment";
    }
}
