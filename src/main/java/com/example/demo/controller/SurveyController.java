package com.example.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Entity.Survey;
import com.example.demo.dto.Engineer;
import com.example.demo.dto.Remedial;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.SurveyRepository;
import com.example.demo.service.SurveyService;
import io.swagger.v3.oas.annotations.Operation;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.io.ByteArrayOutputStream;
import java.util.Optional;



@RestController
@RequestMapping("/api")
public class SurveyController {
	
	@Autowired
	private SurveyRepository surveyRepository;
	
	@Autowired
	private SurveyService surveyService;
	
	 @PostMapping("/addSurveyDetails")
	 @Operation(description ="Post api to add survey details") 
	    public ResponseEntity<Survey> addSurvey(@RequestBody Survey survey) {
	        Survey savedSurvey = surveyRepository.save(survey);
	        return ResponseEntity.ok(savedSurvey);
	    }
	 
	 
	 @GetMapping("/getSurveyDetails")
	 @Operation(description ="Get api to get all survey details") 
	 public List<Survey> getAllSurveys() {
	        return surveyRepository.findAll();
	 }
	
	 @GetMapping("/getSurvey/FD")
	 @Operation(description ="Get api to get FD survey details") 
	 public ResponseEntity<List<Survey>> getFDSurvey() {
	     List<Survey> fdSurvey = surveyRepository.findBySurveyTypeIgnoreCase("FD");
	     return ResponseEntity.ok(fdSurvey);
	 }
	 
	 @PutMapping("/assignRemedial/{surveyId}")
	 @Operation(description ="Put api to assign remedial") 
	 public ResponseEntity<Object>assignRemedial(@RequestBody Remedial remedial, @PathVariable(value="surveyId") int surveyId) throws NotFoundException{
		 ResponseEntity<Object>ab=surveyService.assignRemedial(remedial,surveyId);
		 return ab;
	 }
	 
	
	 @PutMapping("/reAssignEngineer/{surveyId}")
	    public ResponseEntity<Survey> reassignEngineer(
	            @PathVariable int surveyId,
	            @RequestBody Engineer request
	    ) {
	        Survey updatedSurvey = surveyService.reassignEngineer(surveyId, request);
	        
	        if (updatedSurvey == null) {
	            return ResponseEntity.notFound().build();
	        }
	        
	        return ResponseEntity.ok(updatedSurvey);
	    }
	 
	 @GetMapping(value = "/report/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
	    public ResponseEntity<byte[]> generateSurveyReport(@PathVariable int id) throws DocumentException {
	        Optional<Survey> surveyOptional = surveyRepository.findById(id);
	        if (surveyOptional.isPresent()) {
	            Survey survey = surveyOptional.get();

	           
	            Document document = new Document();

	            
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();

	            
	            PdfWriter.getInstance(document, baos);

	            
	            document.open();

	            
	            document.add(new Paragraph("User Details"));
	            document.add(new Paragraph("ID: " + survey.getId()));
	            document.add(new Paragraph("SurveyType: " + survey.getSurveyType()));
	            document.add(new Paragraph("SiteName: " + survey.getSiteName()));
	            document.add(new Paragraph("SiteAddress: " + survey.getSiteAddress()));
	            document.add(new Paragraph("DateTime: " + survey.getDateTime()));
	            document.add(new Paragraph("Engineer: " + survey.getEngineer()));
	            
	            
	            document.close();

	            
	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(MediaType.APPLICATION_PDF);
	            headers.setContentDispositionFormData("attachment", "survey_report.pdf");

	            
	            return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
	        } else {
	            return ResponseEntity.notFound().build();
	        }}
}
