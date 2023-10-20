package com.emad.spring.Service;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;


import com.emad.spring.Entity.Course;
import com.emad.spring.Entity.InstructorDetails;
import com.emad.spring.Exceptions.ObjectNotFoundException;
import org.hibernate.ObjectDeletedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.emad.spring.Dao.InstructorRepository;
import com.emad.spring.Entity.Instructor;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InstructorServiceImplTest {
	@Mock
	private InstructorRepository instructorRepository;

	@Mock
	private InstructorDetailsServiceImpl instructorDetailsServiceImpl;
	@Mock
	private CourseServiceImpl courseServiceImpl;
	@InjectMocks
	private static InstructorServiceImpl serviceUnderTest;
	@Captor
	private ArgumentCaptor<Instructor> instArgumentCaptor ;

	private static Instructor mockInstructor;

	@BeforeEach
	void setup(){
		mockInstructor = new Instructor("Imad","alkhawam","mail@gmail.com");
		mockInstructor.setId(1);

	}

	@Test
	public void testCanGetAllInstructors() {
	    // given
	    List<Instructor> mockList = new ArrayList<>();
	    mockList.add(mockInstructor);

	    // Configure the mock repository to return the mockList when findAll() is called
	    when(instructorRepository.findAll()).thenReturn(mockList);

	    // When
	    List<Instructor> result = serviceUnderTest.getAllInstructors();

	    // Then
	    verify(instructorRepository).findAll(); // Expecting a single call to findAll()
	    assertEquals(mockList, result); // Compare the result with the mockList
	}

	@Test
	void testCanCreateInstructor() {
		//when
		when(instructorRepository.save(mockInstructor)).thenReturn(mockInstructor);
		serviceUnderTest.createInstructor(mockInstructor);
		
		//then 
		verify(instructorRepository).save(instArgumentCaptor.capture());
		
		Instructor tempInstructor = instArgumentCaptor.getValue();		
		assertThat(tempInstructor).isEqualTo(mockInstructor);
		
	}


	@Test
	public void testCanGetInstructorById(){
		//given
		int instructorId = 1;
		//when
		when(instructorRepository.findById(instructorId)).thenReturn(Optional.ofNullable(mockInstructor));
		serviceUnderTest.getInstructorById(instructorId);
		// then

		verify(instructorRepository).findById(instructorId);

	}

	@Test
	public void testCanThrowExceptionIfObjectIsNull(){
		//given
		int instId = 1;
		// when
		when(instructorRepository.findById(instId)).thenReturn(Optional.empty());
		assertThrows(ObjectNotFoundException.class , ()-> {
			serviceUnderTest.getInstructorById(instId);
		});
		verify(instructorRepository).findById(instId);
	}

	@Test
	public void testCanUpdateInstructor(){
		int instructorId = 1;
		Instructor existingInstructor = new Instructor( "ExistingFirstName", "ExistingLastName", "existing@example.com");
		Instructor updatedInstructor = new Instructor( "NewFirstName", "NewLastName", "new@example.com");

		// Mocking the behavior of the repository
		when(instructorRepository.findById(instructorId)).thenReturn(Optional.of(existingInstructor));
		when(instructorRepository.save(any(Instructor.class))).thenAnswer(invocation -> invocation.getArgument(0));

		// Act
		System.out.println(existingInstructor);
		Instructor result = serviceUnderTest.updateInstructor(updatedInstructor, instructorId);
		System.out.println(existingInstructor);
		// Assert
		verify(instructorRepository).findById(instructorId);
		verify(instructorRepository).save(existingInstructor); // Ensure the save method was called with the existing instructor

		assertThat(result).isNotNull();
		assertThat(result.getFirstName()).isEqualTo(updatedInstructor.getFirstName());
		assertThat(result.getLastName()).isEqualTo(updatedInstructor.getLastName());
		assertThat(result.getEmail()).isEqualTo(updatedInstructor.getEmail());
	}


	@Test
	public void testCanDeleteInstructor() {
		// Given
		int instructorId = 1;

		// Mocking the behavior of the repository
		when(instructorRepository.findById(instructorId)).thenReturn(Optional.of(mockInstructor));

		// Act
		assertDoesNotThrow(() -> serviceUnderTest.deleteInstructor(instructorId));

		// Assert
		verify(instructorRepository).findById(instructorId);
		verify(instructorRepository).deleteById(instructorId);
	}


	@Test
	public void testSetInstructorDetails() {
		// Arrange
		int instructorId = 1;
		int instructorDetailsId = 2;

		// Mock the behavior of the repository
		Instructor tempInstructor = new Instructor();
		when(instructorRepository.findById(instructorId)).thenReturn(Optional.of(tempInstructor));

		InstructorDetails tempInstructorDetails = new InstructorDetails("code123","coding");
		when(instructorDetailsServiceImpl.getInstructorDetailsById(instructorDetailsId)).thenReturn(tempInstructorDetails);

		// Act
		serviceUnderTest.setInstructorDetails(instructorId, instructorDetailsId);

		// Assert
		verify(instructorRepository).findById(instructorId);
		verify(instructorDetailsServiceImpl).getInstructorDetailsById(instructorDetailsId);
		verify(instructorRepository).save(tempInstructor);
		assertThat(tempInstructor.getInstructorDetails()).isEqualTo(tempInstructorDetails);
	}


	@Test
	public void testAddCourse() {
		// Arrange
		int instructorId = 1;
		int courseId = 2;

		// Create a new Instructor instance for the test
		Instructor tempInstructor = new Instructor("TestFirstName", "TestLastName", "test@example.com");

		// Log the initial state of the tempInstructor
		System.out.println("Initial tempInstructor: " + tempInstructor);

		when(instructorRepository.findById(instructorId)).thenReturn(Optional.of(tempInstructor));

		Course tempCourse = new Course("programming");
		when(courseServiceImpl.findCourseById(courseId)).thenReturn(tempCourse);

		// Act
		Instructor result = serviceUnderTest.addCourse(instructorId, courseId);

		// Assert
		verify(instructorRepository).findById(instructorId);
		verify(courseServiceImpl).findCourseById(courseId);

		// Log the state of tempInstructor after the method call
		System.out.println("tempInstructor after method call: " + tempInstructor);

		verify(instructorRepository).save(tempInstructor);
		assertThat(result).isEqualTo(tempInstructor);
		assertThat(tempInstructor.getCourses()).contains(tempCourse);
	}
}

