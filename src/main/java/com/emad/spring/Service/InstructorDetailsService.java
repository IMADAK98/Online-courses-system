package com.emad.spring.Service;

import java.util.List;

import com.emad.spring.Entity.InstructorDetails;

public interface InstructorDetailsService{
	
	void createInstructorDetails(InstructorDetails instructorDetails);
	List<InstructorDetails> getAllInstructorDetails();
	InstructorDetails getInstructorDetailsById(int id );
	void deleteInstructorDetails(int id);
	InstructorDetails updateInstructorDetails(InstructorDetails instructorDetails , int id);
}
