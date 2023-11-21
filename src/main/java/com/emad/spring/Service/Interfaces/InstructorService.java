package com.emad.spring.Service.Interfaces;

import com.emad.spring.Entity.Instructor;

public interface InstructorService extends CrudService<Instructor,Integer> {

	void setInstructorDetails(int instructorId, int instructorDetailsId);

	Instructor addCourse(int instructorId, int courseId);
}
