package com.emad.spring.Entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;

@Entity
@Table(name = "course")
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id ;
	@Column(name = "title")
	private String title;
	@ManyToOne(cascade = {
			CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.PERSIST,
			CascadeType.REFRESH
	})
	@JoinColumn(name = "instructor_id")
	@JsonBackReference
	private Instructor instructor;
	
	@ManyToMany(fetch = FetchType.LAZY , cascade = {
			   CascadeType.DETACH,
			   CascadeType.PERSIST,
			   CascadeType.MERGE,
			   CascadeType.REFRESH
	})
	@JoinTable(name = "course_student" ,joinColumns = @JoinColumn(name="course_id"),
	inverseJoinColumns = @JoinColumn(name="student_id"))

	private List<Student> students;

	@OneToOne(cascade = CascadeType.ALL,
			mappedBy = "course"
	)
	@JsonManagedReference
	private Review review ;
	
	
	public void addStudent(Student student) {
		if (students==null) {
			students= new ArrayList<>();
		}
		students.add(student);
	}
	
	
	public Course() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Course(String title) {
		super();
		this.title = title;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Instructor getInstructor() {
		return instructor;
	}
	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}
	
	public List<Student> getStudents() {
		return students;
	}


	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", title=" + title + ", instructor=" + instructor + "]";
	}
	
	
	

}
