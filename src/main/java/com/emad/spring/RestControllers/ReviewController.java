package com.emad.spring.RestControllers;
import com.emad.spring.Entity.Review;
import com.emad.spring.Service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    @GetMapping("/Reviews")
    public List<Review> getAllReviews(){
        return reviewService.getAllReviews();
    }

    @GetMapping("/Reviews/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable int id) {
        return new ResponseEntity<>(reviewService.getReviewById(id)
                , HttpStatus.OK);
    }

    @PostMapping("/Reviews")
    public ResponseEntity<Review> createReview( @RequestBody  Review review  ) {
        Review tempReview =reviewService.createReview(review);
        return new ResponseEntity<>(tempReview ,HttpStatus.OK);
    }

    @PutMapping("/Reviews/{id}")
    public ResponseEntity<Review> updateInstructor(@Valid @RequestBody Review review , @PathVariable int id) {
        return new  ResponseEntity<Review>
                (reviewService.updateReview(review , id)
                        ,HttpStatus.OK);
    }

    @DeleteMapping("Reviews/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable int id) {
        return ResponseEntity.ok().build();
    }


    @PostMapping("Reviews/Courses/{reviewId}/{courseId}")
    public ResponseEntity<Review> setCourseReview(@PathVariable int courseId , @PathVariable int reviewId){

        Review tempReview = reviewService.setCourseReview(reviewId,courseId);
        return new ResponseEntity<>(tempReview, HttpStatus.OK);
    }

}
