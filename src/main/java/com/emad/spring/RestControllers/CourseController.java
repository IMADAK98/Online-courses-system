package com.emad.spring.RestControllers;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.emad.spring.Entity.Course;
import com.emad.spring.Service.Implementation.CourseServiceImpl;
@RestController
@RequestMapping("/api/v1")
public class CourseController  {
	
	private final CourseServiceImpl courseServiceImpl ;
	

	
	public CourseController(CourseServiceImpl courseServiceImpl) {
		super();
		this.courseServiceImpl = courseServiceImpl;
	}


	@GetMapping("/Courses")
	public List<Course> getAllCourses(){
		return courseServiceImpl.getAll();
	}
	
	@GetMapping("Courses/{id}")
	public ResponseEntity<Course> getCoursesById(@PathVariable int id){
		return new ResponseEntity<Course>(courseServiceImpl.getById(id),HttpStatus.OK);
	}
	
	@PostMapping("/Courses")
	public ResponseEntity<Course> createCourses(@RequestBody Course course) {
		return new ResponseEntity<Course>(courseServiceImpl.create(course), HttpStatus.OK);
		
	}
	
	@PutMapping("/Courses/{courseID}")
	public ResponseEntity<Course> updateCourse(@RequestBody Course course ,@PathVariable int courseID){
		return new ResponseEntity<>(courseServiceImpl.update(course, courseID), HttpStatus.OK);
	}
	
	@DeleteMapping("/Courses/course/{courseID}")
	public ResponseEntity<Void> deleteCourse(@PathVariable int courseID){
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("Courses/{courseId}/{studentId}")
	public ResponseEntity<Course>addStudent(@PathVariable int courseId , @PathVariable int studentId){
		return new ResponseEntity<Course>(courseServiceImpl.setStudent(courseId, studentId),HttpStatus.OK);		
	}
	
	@GetMapping("Courses/Students/{studentId}/courses")
	public List<Course> findCourseByStudentsId(@PathVariable int studentId){
		return courseServiceImpl.findCourseByStudentsId(studentId);
	}

	@PostMapping("Courses/Reviews/{courseId}/{reviewId}")
	public ResponseEntity<Course> setCourseReview(@PathVariable int courseId , @PathVariable int reviewId){

		Course tempCourse = courseServiceImpl.setCourseForReview(courseId,reviewId);
		return new ResponseEntity<>(tempCourse, HttpStatus.OK);
	}



}
