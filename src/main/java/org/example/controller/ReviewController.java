package org.example.controller;

import org.example.model.Doctor;
import org.example.model.Review;
import org.example.service.PatientService;
import org.example.service.ReviewService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;
    private final PatientService patientService;
    private final UserService userService;

    @Autowired
    ReviewController(ReviewService reviewService, PatientService patientService, UserService userService){
        this.reviewService = reviewService;
        this.patientService = patientService;
        this.userService = userService;
    }

    @GetMapping("/patients/review")
    public String getReviewPage(@ModelAttribute Review review, Model model){
        Doctor doctor = patientService.findByUser(userService.getCurrentUser().getId()).getDoctor();
        model.addAttribute("currentUser", userService.getCurrentUser());
        if (doctor == null)
            return "patient/no_doctor";
        model.addAttribute(doctor);
        return "patient/review";
    }

    @PostMapping("/patients/review")
    public String createReview(@ModelAttribute Review review, Model model){
        Doctor doctor = patientService.findByUser(userService.getCurrentUser().getId()).getDoctor();
        model.addAttribute("currentUser", userService.getCurrentUser());
        review.setDoctor(patientService.findByUser(userService.getCurrentUser().getId()).getDoctor());
        reviewService.createEntity(review);
        model.addAttribute(doctor);
        return "patient/review";
    }
}