package com.emad.spring.RestControllers;

import com.emad.spring.Entity.Student;
import com.emad.spring.Service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
	@GetMapping("Students/courses/{courseId}/courses")
	  public List<Student> findStudentByCoursesId(@PathVariable int courseId) {
        return studentServiceImpl.findStudentByCoursesId(courseId);
	    
	  }
}
