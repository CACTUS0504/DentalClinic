package org.example.service;

import org.example.model.Prescription;
import org.example.model.Review;
import org.example.repository.PrescriptionRepository;
import org.example.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService implements TableService<Review>{

    private ReviewRepository reviewRepository;

    @Autowired
    ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void createEntity(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public List<Review> readAllEntity() {
        return reviewRepository.findAll();
    }

    @Override
    public Review readOneEntity(long id) {
        return reviewRepository.getById(id);
    }

    @Override
    public boolean updateEntity(Review review, long id) {
        review.setId(id);
        reviewRepository.save(review);
        return true;
    }

    @Override
    public boolean deleteEntity(long id) {
        reviewRepository.deleteById(id);
        return true;
    }
}