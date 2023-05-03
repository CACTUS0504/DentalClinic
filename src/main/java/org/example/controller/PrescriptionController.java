package org.example.controller;

import org.example.model.Prescription;
import org.example.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @Autowired
    PrescriptionController(PrescriptionService prescriptionService){
        this.prescriptionService = prescriptionService;
    }

    // Добавить view
    @GetMapping(value="")
    @ResponseBody
    public List<Prescription> readAll(Model model) {
        model.addAttribute("prescriptions", prescriptionService.readAllEntity());
        return prescriptionService.readAllEntity();
    }
}