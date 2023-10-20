package com.emad.spring.Service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emad.spring.Entity.Course;
import com.emad.spring.Entity.Student;

public interface StudentService  {
	
	List<Student>getAllStudents();
	Student createStudent(Student student);
	Student findStudentById(int id );
	Student updateStudent(Student student , int id );
	void deleteStudent(int id);
	Student setCourse(int studentId, int courseId);
	List<Student> findStudentByCoursesId(int id);
}
