package com.emad.spring.Service;

import java.util.List;

import com.emad.spring.Entity.Course;

public interface CourseService {

	List<Course> getAllCourses();
	Course createCourse(Course course);
	Course findCourseById(int id );
	Course updateCourse(Course course , int id );
	void deleteCourse(int courseId);
	Course setStudent(int courseId, int studentId);
	List<Course> findCourseByStudentsId(int id);

	Course setCourseForReview(int courseId, int reviewId);
}
