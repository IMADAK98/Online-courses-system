package com.emad.spring.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import com.emad.spring.Dao.StudentRepository;
import com.emad.spring.Entity.Course;
import com.emad.spring.Entity.Instructor;
import com.emad.spring.Entity.Student;
import com.emad.spring.Exceptions.InvalidIdException;
import com.emad.spring.Exceptions.ObjectNotFoundException;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
	
	private  StudentRepository studentRepository;
	private  CourseServiceImpl courseServiceImpl;
	@Autowired
	public StudentServiceImpl(StudentRepository studentRepository , CourseServiceImpl courseServiceImpl) {
		super();
		this.studentRepository = studentRepository;
		this.courseServiceImpl = courseServiceImpl;
	}

	public StudentServiceImpl(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public StudentServiceImpl(CourseServiceImpl courseServiceImpl) {
		this.courseServiceImpl = courseServiceImpl;
	}

	public StudentServiceImpl() {
	}

	@Override
	public Student createStudent(Student student) {
		return studentRepository.save(student);
	}

	@Override
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	@Override
	public Student findStudentById(int studentId) {

		return studentRepository.findById(studentId)
				.orElseThrow(()->new ObjectNotFoundException("Student object not found"));
	}

	@Override
	public Student updateStudent(Student student, int studentId) {
		validateId(studentId);
		Student tempStudent = studentRepository.findById(studentId)
				.orElseThrow(()-> new ObjectNotFoundException("Student object not found"));
		tempStudent.setFirstName(student.getFirstName());
		tempStudent.setLastName(student.getLastName());
		tempStudent.setEmail(student.getEmail());
		studentRepository.save(tempStudent);
		return tempStudent;
	}

	@Override
	public void deleteStudent(int studentId) {
		validateId(studentId);
		 studentRepository.findById(studentId).ifPresentOrElse(
				 value-> {
					 value.setCourses(null);
					 studentRepository.deleteById(value.getId());
				 },
				 ()->{throw new ObjectNotFoundException("Student not found");}
		 );
	}

	@Override
	public Student setCourse(int studentId, int courseId) {
		validateId(studentId,courseId);
		Student tempStudent = studentRepository.findById(studentId)
				.orElseThrow(()->new ObjectNotFoundException("Student object not found"));
		Course tempCourse = courseServiceImpl.findCourseById(courseId);
		tempStudent.addCourses(tempCourse);
		return studentRepository.save(tempStudent);
	}

	@Override
	public List<Student> findStudentByCoursesId(int id) {
		return studentRepository.findStudentByCoursesId(id);
	}



	public void validateId(int... ids){
		for (int id : ids){
			if (id<=0){
				throw new InvalidIdException("Invalid Id: " + id);
			}
		}
	}

	
	
	
}
