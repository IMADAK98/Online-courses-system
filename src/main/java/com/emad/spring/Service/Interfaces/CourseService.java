package com.emad.spring.Service.Interfaces;

import java.util.List;

import com.emad.spring.Entity.Course;

public interface CourseService extends CrudService<Course,Integer> {

	Course setStudent(int courseId, int studentId);
	List<Course> findCourseByStudentsId(int id);

	Course setCourseForReview(int courseId, int reviewId);
}
