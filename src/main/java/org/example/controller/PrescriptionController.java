package org.example.controller;

import org.example.model.*;
import org.example.service.DoctorService;
import org.example.service.PatientService;
import org.example.service.PrescriptionService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final UserService userService;
    private final DoctorService doctorService;
    private final PatientService patientService;

    @Autowired
    PrescriptionController(PrescriptionService prescriptionService, UserService userService, DoctorService doctorService, PatientService patientService){
        this.prescriptionService = prescriptionService;
        this.userService = userService;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    @GetMapping("/doctor/prescription")
    public String returnDoctorPrescription(Model model) {
        User currentUser = userService.getCurrentUser();
        Doctor doctor = doctorService.findByUser(currentUser.getId());
        model.addAttribute("patients", doctor.getPatients());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("prescription", new Prescription());
        if (doctor == null) return "/patient/no_doctor";
        return "doctor/create_prescription";
    }

    @PostMapping("/doctor/prescription")
    public String createPrescription(@ModelAttribute Prescription prescription, Model model){
        prescriptionService.createEntity(prescription);

        User currentUser = userService.getCurrentUser();
        Doctor doctor = doctorService.findByUser(currentUser.getId());
        model.addAttribute("patients", doctor.getPatients());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("prescription", new Prescription());
        return "doctor/create_prescription";
    }

    @GetMapping("/patient/prescription")
    public String returnPatientPrescription(Model model) {
        User currentUser = userService.getCurrentUser();
        Patient patient = patientService.findByUser(userService.getCurrentUser().getId());
        List<Prescription> prescriptions = patient.getPrescriptions();
        model.addAttribute("currentUser", currentUser);
        if (prescriptions.isEmpty()) return "patient/no_prescription.html";
        model.addAttribute("prescriptions", prescriptions);
        return "patient/prescription";
    }
}