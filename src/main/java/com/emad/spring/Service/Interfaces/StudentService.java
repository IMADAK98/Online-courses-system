package com.emad.spring.Service.Interfaces;

import java.util.List;
import com.emad.spring.Entity.Student;

public interface StudentService extends CrudService<Student,Integer>  {

	Student setCourse(int studentId, int courseId);
	List<Student> findStudentByCoursesId(int id);
}
