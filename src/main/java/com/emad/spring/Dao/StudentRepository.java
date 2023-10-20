package com.emad.spring.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emad.spring.Entity.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

	List<Student> findStudentByCoursesId(int id);
}
