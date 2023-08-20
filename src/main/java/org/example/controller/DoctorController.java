package org.example.controller;

import org.example.model.Doctor;
import org.example.model.Review;
import org.example.model.User;
import org.example.service.DoctorService;
import org.example.service.ReviewService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/doctor")
public class DoctorController {

    private final DoctorService doctorService;
    private final UserService userService;
    private final ReviewService reviewService;

    @Autowired
    DoctorController(DoctorService doctorService, UserService userService, ReviewService reviewService){
        this.doctorService = doctorService;
        this.userService = userService;
        this.reviewService = reviewService;
    }

    // Добавить view
    @ResponseBody
    @GetMapping(value="")
    public List<Doctor> readAll(Model model) {
        model.addAttribute("patients", doctorService.readAllEntity());
        return doctorService.readAllEntity();
    }

    @GetMapping(value="/cabinet")
    public String showHome(Model model) {
        User currentUser = userService.getCurrentUser();
        Doctor doctor = doctorService.findByUser(currentUser.getId());
        List<Review> reviews = reviewService.findAllByDoctorId(doctor.getId());

        double totalRating = 0;
        for(Review review : reviews){
            totalRating += review.getRating();
        }
        double avgRating = Math.round(totalRating / reviews.size() * 10) / 10d;

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentDoctor", doctor);
        if (avgRating != 0){
            model.addAttribute("rating", avgRating);
        }
        else {
            model.addAttribute("rating", "У вас пока ещё нет оценок");
        }
        if (doctor.getPatients().isEmpty()){
            model.addAttribute("patients", null);
        } else {
            model.addAttribute("patients", doctor.getPatients());
        }

        return "doctor/cabinet";
    }
}
