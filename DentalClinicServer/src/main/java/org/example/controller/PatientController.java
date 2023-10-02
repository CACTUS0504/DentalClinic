package org.example.controller;

import org.example.model.*;
import org.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService patientService;
    private final UserService userService;
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    private final ReviewService reviewService;

    @Autowired
    PatientController(PatientService patientService, UserService userService, DoctorService doctorService, AppointmentService appointmentService, ReviewService reviewService){
        this.patientService = patientService;
        this.userService = userService;
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
        this.reviewService = reviewService;
    }

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

        return "patient/cabinet";
    }

    @GetMapping(value = "/change-doctor")
    public String returnChangeDoctorPage(Model model){
        User currentUser = userService.getCurrentUser();
        List<Doctor> doctors = doctorService.readAllEntity();
        HashMap<Doctor, Double> reviewedDoctors= new HashMap<>();

        for(Doctor doctor : doctors){
            List<Review> reviews = reviewService.findAllByDoctorId(doctor.getId());
            double totalRating = 0;
            for(Review review : reviews){
                totalRating += review.getRating();
            }
            double avgRating = Math.round(totalRating / reviews.size() * 10) / 10d;
            reviewedDoctors.put(doctor, avgRating);
        }
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("reviewedDoctors", reviewedDoctors);
        return "patient/change_doctor";
    }

    @GetMapping(value = "/change-doctor/{id}")
    public String returnChangeDoctorPage(Model model, @PathVariable(name = "id") long id){
        Patient patient = patientService.findByUser(userService.getCurrentUser().getId());
        patient.setDoctor(doctorService.readOneEntity(id));
        patientService.updateEntity(patient, patient.getId());
        model.addAttribute("currentUser", userService.getCurrentUser());
        return "patient/doctor_changed";
    }
}
