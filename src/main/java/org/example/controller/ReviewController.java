package org.example.controller;

import org.example.model.Review;
import org.example.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    // Добавить view
    @GetMapping(value="")
    @ResponseBody
    public List<Review> readAll(Model model) {
        model.addAttribute("reviews", reviewService.readAllEntity());
        return reviewService.readAllEntity();
    }
}