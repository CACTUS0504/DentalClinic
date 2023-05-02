package org.example.controller;

import org.example.model.Doctor;
import org.example.service.DoctorService;
import org.example.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    DoctorController(DoctorService doctorService){
        this.doctorService = doctorService;
    }

    // Добавить view
    @ResponseBody
    @GetMapping(value="")
    public List<Doctor> readAll(Model model) {
        model.addAttribute("patients", doctorService.readAllEntity());
        return doctorService.readAllEntity();
    }
}
