package com.emad.spring;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import com.emad.spring.Dao.InstructorRepository;
import com.emad.spring.Entity.Instructor;
import com.emad.spring.RestControllers.InstructorController;
import com.emad.spring.Service.InstructorServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = InstructorController.class)
public class InstructorControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private static InstructorServiceImpl instructorServiceImpl;

	@MockBean
	private static InstructorRepository instructorRepository;
	
	private static Instructor mockInstructor;

	private static  ArrayList<Instructor> mockList;
	
	
	@BeforeEach
	public void setUp() {
		mockInstructor = new
				Instructor("imad", "issam", "valid@gmail.com");
		mockList = new ArrayList<>();
		mockList.add(mockInstructor);
		when(instructorServiceImpl.getAllInstructors()).thenReturn(mockList);
	}



	@Test
	public void testGetInstructorByIdController() throws Exception {
		// Mock data
		int instructorId = 1;
		// Mock the service method to return the mockInstructor when called with
		// instructorId
		mockInstructor.setId(instructorId);
		when(instructorServiceImpl.getInstructorById(instructorId)).thenReturn(mockInstructor);

		// Perform the GET request
		mockMvc.perform(
				get(UrlConstants.GET_BY_ID_MAPPING , instructorId))
				.andExpect(status().isOk()) // Expect HTTP 200 OK
				.andExpect(
						content()
								.contentType
										("application/json"))
				.andExpect(
						jsonPath
								("$.id").value(instructorId))
				.andExpect(
						jsonPath
								("$.firstName").value("imad"));

	}

	@Test
	public void testCanGetALLInstructorsController() throws Exception {

		// then
		mockMvc.perform(
				get(UrlConstants.GET_ALL_MAPPING))
				.andExpect(status().isOk())
				.andExpect(
						 content()
						.contentType("application/json")
				)
				.andExpect(
						jsonPath("$[0].firstName",is(mockInstructor.getFirstName()))
				)
				.andExpect(
						jsonPath("$[0].lastName", is(mockInstructor.getLastName()))
				)
				.andExpect(
						jsonPath("[0].email",is(mockInstructor.getEmail()))
				);

	}

	@Test
	public void testCanCreateInstructor() throws Exception {
		
		// when then
		mockMvc.perform(
				post(UrlConstants.POST_MAPPING)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(mockInstructor))
				).andExpect(
						status().isOk()
				)

		;

	}

	@Test
	public void testCanCreateInstructor_IsValid() throws Exception {

		mockMvc.perform(
				post(UrlConstants.POST_MAPPING).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(mockInstructor))
				).andExpect(
						status().isOk()
				)
		;

	}

	@Test
	public void whenValidInput_ThenMapToServiceImpl() throws Exception {
		// when
		 mockMvc.perform(
				 post(UrlConstants.POST_MAPPING)
						 .contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(mockInstructor))
				 )
		 .andExpect(status().isOk());

		 // then
		ArgumentCaptor<Instructor> instructorCaptor = ArgumentCaptor.forClass(Instructor.class);

		verify(instructorServiceImpl).createInstructor(instructorCaptor.capture());
		assertThat(instructorCaptor.getValue().getFirstName()).isEqualTo("imad");
		assertThat(instructorCaptor.getValue().getLastName()).isEqualTo("issam");
		assertThat(instructorCaptor.getValue().getEmail()).isEqualTo("valid@gmail.com");

	}



	@Test
	public void testDoesDeleteGetCalled() throws Exception { // given
		int id = 1;
		// when
		mockMvc.perform(
				delete(UrlConstants.DELETE_MAPPING, id)				
				).andExpect(status().isOk());

		verify(instructorServiceImpl,times(1)).deleteInstructor(id);
	}
}

	

















/*	@Test
public void whenValidInput_ThenReturnObject() throws Exception {

	// when then
	
//	
		ResultActions result = mockMvc
			.perform(
					post(UrlConstants.POST_MAPPING)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(mockInstructor))
					)	
			.andExpect(status().isOk())
			;
	
	when(instructorServiceImpl.createInstructor(mockInstructor)).thenCallRealMethod();

	String string = result.andReturn().getRequest().getContentAsString();
	String string2 = result.andReturn().getResponse().getContentAsString();
	

	System.out.println(string);
	System.out.println("response is : " + string2);
	
	////// not working properly 
	

	;

}*/