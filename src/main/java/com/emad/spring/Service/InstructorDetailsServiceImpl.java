package com.emad.spring.Service;

import com.emad.spring.Dao.InstructorDetailsRepository;
import com.emad.spring.Entity.InstructorDetails;
import com.emad.spring.Exceptions.InvalidIdException;
import com.emad.spring.Exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class InstructorDetailsServiceImpl implements InstructorDetailsService {

	private final InstructorDetailsRepository instructorDetailsRepository;

	
	
	@Autowired
	public InstructorDetailsServiceImpl(
			InstructorDetailsRepository instructorDetailsRepository)
	{
		super();
		this.instructorDetailsRepository = instructorDetailsRepository;
	}


	@Override
	public void createInstructorDetails(InstructorDetails instructorDetails) {
		instructorDetailsRepository.save(instructorDetails);
		
	}


	@Override
	public List<InstructorDetails> getAllInstructorDetails() {
		return instructorDetailsRepository.findAll();
	}
	

	@Override
	public InstructorDetails getInstructorDetailsById(int InstructoDetailsId)  {
		validateId(InstructoDetailsId);
		return instructorDetailsRepository.findById(InstructoDetailsId)
				.orElseThrow(()-> new ObjectNotFoundException("Object is null"));
	}


	@Override
	public void deleteInstructorDetails (int instructorDetailsId){
		validateId(instructorDetailsId);
		instructorDetailsRepository.findById(instructorDetailsId)
				.ifPresentOrElse(
						tempInstDetails ->
				{
					tempInstDetails.getInstructor().setInstructorDetails(null);
					instructorDetailsRepository.save(tempInstDetails);
				},
				()-> {throw new ObjectNotFoundException("Null Instructor Details Object !!");}
		);
	}



	@Override
	public InstructorDetails updateInstructorDetails(InstructorDetails instructorDetails, int instructorDetailsId){
		validateId(instructorDetailsId);
		return instructorDetailsRepository.findById(instructorDetailsId)
				.map(
						value-> {
							value.setHobby(instructorDetails.getHobby());
							value.setYoutubeChannel(instructorDetails.getYoutubeChannel());
							value.getInstructor().setInstructorDetails(value);
							return instructorDetailsRepository.save(value);
						}

				)
				.orElseThrow(
						()-> new ObjectNotFoundException("Instructor Details object doesnt exist")
				);

    }





	public void validateId(int... ids){
		for (int id : ids){
			if (id<=0){
				throw new InvalidIdException("Invalid Id: " + id);
			}
		}
	}



	
	
	
	
}
