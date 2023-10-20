package com.emad.spring.RestControllers;

import com.emad.spring.Entity.InstructorDetails;
import com.emad.spring.Service.InstructorDetailsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class InstructorDetailsController {
	
	private final InstructorDetailsServiceImpl instructorDetailsServiceImpl;

	public InstructorDetailsController(InstructorDetailsServiceImpl instructorDetailsServiceImpl) {
		super();
		this.instructorDetailsServiceImpl = instructorDetailsServiceImpl;
	}
	
	
	@GetMapping("/Instructor_Details")
	public List<InstructorDetails> getAllInstructorDetails(){
		return instructorDetailsServiceImpl.getAllInstructorDetails();
	}
	
	@GetMapping("/Instructor_Details/{id}")
	public ResponseEntity<InstructorDetails> getInstructorDetailById(@PathVariable int id) {
		return new ResponseEntity<>(instructorDetailsServiceImpl.getInstructorDetailsById(id),
				HttpStatus.OK);
	}
	
	@PostMapping("/Instructor_Details")
	public ResponseEntity<Void> createInstructorDetails(@RequestBody InstructorDetails instructorDetails) {
		instructorDetailsServiceImpl.createInstructorDetails(instructorDetails);
		return ResponseEntity.ok().build();
	}
	
	
	@PostMapping("/Instructor_Details/{id}")
	public ResponseEntity<InstructorDetails> updateInstructorDetails(@RequestBody InstructorDetails instructorDetails
			,@PathVariable int instructorDetailsId) {
		return new ResponseEntity<>
				(instructorDetailsServiceImpl.
						updateInstructorDetails(instructorDetails , instructorDetailsId)
						,HttpStatus.OK);
	}
	
	
	@DeleteMapping("/Instructor_Details/{id}")
	public ResponseEntity<Void> deleteInstructorDetails(@PathVariable int id) {
		instructorDetailsServiceImpl.deleteInstructorDetails(id);
		return ResponseEntity.ok().build();
	}
	
	
}
