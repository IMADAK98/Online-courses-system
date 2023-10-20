package com.emad.spring.Service;

import java.util.List;

import com.emad.spring.Entity.Course;
import com.emad.spring.Entity.Instructor;
import com.emad.spring.Exceptions.InvalidIdException;

public interface InstructorService {

	List<Instructor> getAllInstructors();

	Instructor getInstructorById(int id) throws InvalidIdException;

	Instructor createInstructor(Instructor instructor);

	void deleteInstructor(int id);

	Instructor updateInstructor(Instructor instructor , int id);

	void setInstructorDetails(int instructorId, int instructorDetailsId);

	Instructor addCourse(int instructorId, int courseId);
}
