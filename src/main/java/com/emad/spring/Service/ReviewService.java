package com.emad.spring.Service;

import com.emad.spring.Entity.Review;

import java.util.List;

public interface ReviewService {

    List<Review> getAllReviews();

    Review getReviewById(int reviewId);

    Review createReview(Review review);

    Review updateReview(Review review , int reviewId);

     Review setCourseReview (int reviewId , int courseId);
    void deleteReview(int reviewId);


}
