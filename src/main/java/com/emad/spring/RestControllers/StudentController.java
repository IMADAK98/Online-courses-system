package com.emad.spring.RestControllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emad.spring.Entity.Course;
import com.emad.spring.Entity.Student;
import com.emad.spring.Service.StudentService;
import com.emad.spring.Service.StudentServiceImpl;

@RestController
@RequestMapping("/api/v1")
public class StudentController {
	
	private final StudentService studentServiceImpl;
	
	public StudentController(StudentService studentServiceImpl) {
		super();
		this.studentServiceImpl = studentServiceImpl;
	}


	@GetMapping("/Students")
	public List<Student> getAllStudents(){
		return studentServiceImpl.getAllStudents() ;
	}
	
	@PostMapping("/Students")
	public ResponseEntity<Student> createStudent(@RequestBody Student student){
		return new ResponseEntity<Student>(studentServiceImpl.createStudent(student), HttpStatus.OK);
	}
	
	@GetMapping("/Students/{id}")
	public ResponseEntity<Student> getStudent(@PathVariable int id) {
		return new ResponseEntity<Student>(studentServiceImpl.findStudentById(id), HttpStatus.OK);
	}
	
	@PostMapping("Students/{id}")
	public ResponseEntity<Student> updateStudent(@RequestBody Student student, @PathVariable int id ){
		return new ResponseEntity<Student>(studentServiceImpl.updateStudent(student, id), HttpStatus.OK);
	}
	
	@DeleteMapping("Students/{id}")
	public ResponseEntity<Void>deleteStudent(@PathVariable int id ){
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("Students/{studentId}/{courseId}")
	public ResponseEntity<Student>setCourse (@PathVariable int studentId , @PathVariable int courseId){
		return new ResponseEntity<Student>(studentServiceImpl.setCourse(studentId, courseId),HttpStatus.OK);
	}
	@GetMapping("Students/{courseId}/courses")
	  public List<Student> findStudentByCoursesId(@PathVariable int courseId) {
	    List<Student> tempList = studentServiceImpl.findStudentByCoursesId(courseId);
	    return tempList;
	    
	  }
}
