package com.emad.spring.Service.Implementation;

import com.emad.spring.Dao.StudentRepository;
import com.emad.spring.Entity.Course;
import com.emad.spring.Entity.Student;
import com.emad.spring.Exceptions.InvalidIdException;
import com.emad.spring.Exceptions.ObjectNotFoundException;
import com.emad.spring.Service.Interfaces.StudentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
	
	private  StudentRepository studentRepository;
	private CourseServiceImpl courseServiceImpl;
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
	public Student create(Student student) {
		return studentRepository.save(student);
	}

	@Override
	public List<Student> getAll() {
		return studentRepository.findAll();
	}

	public Student getById(Integer studentId) {

		return studentRepository.findById(studentId)
				.orElseThrow(()->new ObjectNotFoundException("Student object not found"));
	}

	public Student update(Student student, Integer studentId) {
		validateId(studentId);
		Student tempStudent = studentRepository.findById(studentId)
				.orElseThrow(()-> new ObjectNotFoundException("Student object not found"));
		tempStudent.setFirstName(student.getFirstName());
		tempStudent.setLastName(student.getLastName());
		tempStudent.setEmail(student.getEmail());
		studentRepository.save(tempStudent);
		return tempStudent;
	}

	public void delete(Integer studentId) {
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
		Course tempCourse = courseServiceImpl.getById(courseId);
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
