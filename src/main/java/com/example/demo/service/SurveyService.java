package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.demo.Entity.Survey;
import com.example.demo.dto.Engineer;
import com.example.demo.dto.Remedial;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.SurveyRepository;

@Service
public class SurveyService {
	
	@Autowired
	private SurveyRepository surveyRepository;
	
	public ResponseEntity<Object>assignRemedial(@RequestBody Remedial remedial,int surveyId)throws NotFoundException{
		Map<String, Object>response=new HashMap<>();
		Survey a= surveyRepository.findById(surveyId)
				.orElseThrow(()->new NotFoundException("surveyId"));
		
		a.setEngineer(remedial.getEngineer());
		a.setDateTime(remedial.getDateAndTime());
		
		if(a.getSurveyType().equals("FD")) {
			a.setSurveyType("FDR");
		}
		else {
			a.setSurveyType("FSR");
		}
		
		surveyRepository.save(a);
		
		response.put("status","Success" );
		response.put("message","engineer assign successfuly by admin");
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	

	public Survey reassignEngineer(int surveyId, Engineer request) {
        Optional<Survey> optionalSurvey = surveyRepository.findById(surveyId);

        if (optionalSurvey.isEmpty()) {
            return null;
        }

        Survey survey = optionalSurvey.get();

        String currentEngineer = request.getCurrentEngineer();

    
        if (!survey.getEngineer().equals(currentEngineer)) {
            return null;
        }

        String newEngineer = request.getEngineer();

 
        survey.setEngineer(newEngineer);

        surveyRepository.save(survey);

        return survey;
    }

}
