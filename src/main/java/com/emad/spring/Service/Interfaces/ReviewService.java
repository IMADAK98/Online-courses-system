package com.emad.spring.Service.Interfaces;

import com.emad.spring.Entity.Review;

public interface ReviewService extends CrudService<Review,Integer> {
     Review setCourseReview (int reviewId , int courseId);



}
