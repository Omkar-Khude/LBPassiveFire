package com.example.demo.repository;


import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.Entity.Survey;



@Repository
public interface SurveyRepository extends JpaRepository<Survey, Integer> {

	 List<Survey> findBySurveyTypeIgnoreCase(String surveyType);

	Optional<Survey> findBySiteAddress(String siteAddress);
	 

	 
}
