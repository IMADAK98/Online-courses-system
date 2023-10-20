package com.emad.spring.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emad.spring.Entity.Course;
import com.emad.spring.Entity.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

	List<Course> findCourseByStudentsId(int id);
}
