package com.emad.spring.Service;

import java.util.List;
import java.util.Optional;

import com.emad.spring.Entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.weaving.DefaultContextLoadTimeWeaver;
import org.springframework.stereotype.Service;

import com.emad.spring.Dao.CourseRepository;
import com.emad.spring.Entity.Course;
import com.emad.spring.Entity.Instructor;
import com.emad.spring.Entity.Student;
import com.emad.spring.Exceptions.InvalidIdException;
import com.emad.spring.Exceptions.ObjectNotFoundException;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

	private CourseRepository courseRepository;
	private StudentServiceImpl studentServiceImpl;

	private ReviewServiceImpl reviewServiceImpl;

@Autowired
	public CourseServiceImpl(CourseRepository courseRepository, @Lazy StudentServiceImpl studentServiceImpl,
							 ReviewServiceImpl reviewServiceImpl) {
		super();
		this.courseRepository = courseRepository;
		this.studentServiceImpl = studentServiceImpl;
		this.reviewServiceImpl=reviewServiceImpl;
	}

	public CourseServiceImpl(StudentServiceImpl studentServiceImpl) {
		this.studentServiceImpl = studentServiceImpl;
	}

	public CourseServiceImpl(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	public CourseServiceImpl(ReviewServiceImpl reviewServiceImpl) {
		this.reviewServiceImpl = reviewServiceImpl;
	}

	public CourseServiceImpl() {
	}

	@Override
	public List<Course> getAllCourses() {
		// TODO Auto-generated method stub
		return courseRepository.findAll();
	}

	@Override
	public Course createCourse(Course course) {
		return courseRepository.save(course);
	}

	@Override
	public Course findCourseById(int courseId) {
		validateId(courseId);
		return courseRepository.findById(courseId)
				.orElseThrow( ()-> new ObjectNotFoundException("object is null")
		);
	}
	
	@Override
	public Course updateCourse(Course course , int courseID) {
		validateId(courseID);

		Course existingCourse = courseRepository.findById(courseID)
				.orElseThrow(()-> new ObjectNotFoundException("object is null"));
		existingCourse.setTitle(course.getTitle());
		courseRepository.save(existingCourse);
		return existingCourse;
	}


	public void deleteCourse(int courseId) {
		validateId(courseId);
		courseRepository.findById(courseId)
				.ifPresentOrElse(
						value-> {
							value.setInstructor(null);
							courseRepository.deleteById(value.getId());
						},
						()-> {throw new ObjectNotFoundException("Object not found");}
				);
	}

	@Override
	public Course setStudent(int courseId, int studentId) {
			validateId(courseId, studentId);
			Course tempCourse = courseRepository.findById(courseId)
				.orElseThrow(()-> new ObjectNotFoundException("null course object")
				);
			Student tempStudent = studentServiceImpl.findStudentById(studentId);
			tempCourse.addStudent(tempStudent);

		return courseRepository.save(tempCourse);
	}

	@Override
	public List<Course> findCourseByStudentsId(int id) {
		return courseRepository.findCourseByStudentsId(id);
	}

	@Override
	public Course setCourseForReview(int courseId, int reviewId) {
		validateId(courseId,reviewId);
		Course tempCourse = courseRepository.findById(courseId)
				.orElseThrow(()->new ObjectNotFoundException("Course object is null"));
		Review tempReview =reviewServiceImpl.getReviewById(reviewId);
		tempReview.setCourse(tempCourse);
		return courseRepository.save(tempCourse);
	}


	public void validateId(int... ids){
		for (int id : ids){
			if (id<=0){
				throw new InvalidIdException("Invalid Id: " + id);
			}
		}
	}
	


}
