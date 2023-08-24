package org.example.service;

import org.example.model.Review;
import org.example.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService implements TableService<Review>{

    private final ReviewRepository reviewRepository;

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
        Optional<Review> reviewOptional = reviewRepository.findById(id);
        if (reviewOptional.isPresent()) return reviewOptional.get();
        else throw new IllegalArgumentException("Review with id = " + id + " doesn't exist");
    }

    @Override
    public boolean updateEntity(Review review, long id) {
        review.setId(id);
        reviewRepository.save(review);
        return true;
    }

    @Override
    public boolean deleteEntity(long id) {
        if (reviewRepository.findById(id).isPresent()){
            reviewRepository.deleteById(id);
            return true;
        } else return false;
    }

    public List<Review> findAllByDoctorId(long doctor){
        return reviewRepository.findAllByDoctorId(doctor);
    }
}