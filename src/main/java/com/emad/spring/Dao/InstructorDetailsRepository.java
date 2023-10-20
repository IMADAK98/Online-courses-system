package com.emad.spring.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emad.spring.Entity.InstructorDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorDetailsRepository extends JpaRepository<InstructorDetails, Integer> {

}
