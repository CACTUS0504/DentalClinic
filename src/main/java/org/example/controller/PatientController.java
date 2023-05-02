package org.example.controller;

import org.example.model.Patient;
import org.example.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    PatientController(PatientService patientService){
        this.patientService = patientService;
    }

    // Добавить view
    @GetMapping(value="")
    @ResponseBody
    public List<Patient> readAll(Model model) {
        model.addAttribute("patients", patientService.readAllEntity());
        return patientService.readAllEntity();
    }
}
