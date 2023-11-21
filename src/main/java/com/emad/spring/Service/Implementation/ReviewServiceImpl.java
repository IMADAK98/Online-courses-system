package com.emad.spring.Service.Implementation;

import com.emad.spring.Dao.ReviewRepository;
import com.emad.spring.Entity.Course;
import com.emad.spring.Entity.Review;
import com.emad.spring.Exceptions.InvalidIdException;
import com.emad.spring.Exceptions.ObjectNotFoundException;
import com.emad.spring.Service.Interfaces.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {


    private ReviewRepository reviewRepository;

    private CourseServiceImpl courseService;
    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             @Lazy CourseServiceImpl courseService) {
        this.courseService= courseService;
        this.reviewRepository = reviewRepository;
    }

    public ReviewServiceImpl() {
    }

    @Override
    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    public Review getById(Integer reviewId) {
        validateId(reviewId);
        return reviewRepository.findById(reviewId)
                .orElseThrow(
                        ()->new ObjectNotFoundException("Review object is null")
        );
    }

    @Override
    public Review create(Review review) {
         return reviewRepository.save(review);

    }


    public Review update(Review review , Integer reviewId) {
        validateId(reviewId);
        Review tempReview = reviewRepository.findById(reviewId)
                .orElseThrow(()->new ObjectNotFoundException("Review object is null"));
        tempReview.setComment(review.getComment());
        return reviewRepository.save(tempReview);
    }

    public void delete(Integer reviewId) {
            validateId(reviewId);
            reviewRepository.findById(reviewId)
                .ifPresentOrElse(
                        value-> reviewRepository.deleteById(reviewId),
                        ()-> {throw new ObjectNotFoundException("Review object is null"); }
                );
    }


    public Review setCourseReview (int reviewId , int courseId){
        validateId(reviewId,courseId);
        Review tempReview = reviewRepository.findById(reviewId)
                .orElseThrow(()->new ObjectNotFoundException("Review is null"));
        Course tempCourse = courseService.getById(courseId);

        tempReview.setCourse(tempCourse);
        return reviewRepository.save(tempReview);
    }


    public void validateId(int... ids){
        for (int id : ids){
            if (id<=0){
                throw new InvalidIdException("Invalid Id: " + id);
            }
        }
    }
}
