package com.emad.spring.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emad.spring.Entity.Instructor;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {

}
