package com.example.demo.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import com.example.demo.Entity.Door;
import com.example.demo.Entity.FireRisk;
import com.example.demo.Entity.Remediation;
import com.example.demo.Entity.Survey;
import com.example.demo.repository.SurveyRepository;
import com.lowagie.text.DocumentException;
import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/api")
public class ReportController {
	
	private AtomicInteger reportCounter = new AtomicInteger(1);
	private SurveyRepository surveyRepository;
	 private TemplateEngine templateEngine;
	 @Autowired
	    public ReportController(TemplateEngine templateEngine,SurveyRepository surveyRepository) {
	        this.templateEngine = templateEngine;
	        this.surveyRepository =surveyRepository;   
	    }
    
   	 
//	 @GetMapping("/report")
//	 @Operation(description ="Get api to generate survey report, survey remedial report, fireStopping report and fireStoppingRemedial report by surveyAddress and reportType")
//	 public ResponseEntity<byte[]> generateReport(@RequestParam("siteAddress") String siteAddress, @RequestParam("reportType") String reportType) {
//	     try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
//	         String htmlContent = generateHtmlReport(siteAddress, reportType);
//	         ITextRenderer renderer = new ITextRenderer();
//	         renderer.setDocumentFromString(htmlContent);
//	         renderer.layout();
//	         renderer.createPDF(outputStream);
//	         byte[] pdfBytes = outputStream.toByteArray();
//
//	         HttpHeaders headers = new HttpHeaders();
//	         headers.setContentType(MediaType.APPLICATION_PDF);
//	         headers.setContentDispositionFormData("attachment", "report-details.pdf");
//	         headers.setContentLength(pdfBytes.length);
//
//	         return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
//	     } catch (IOException | DocumentException e) {
//	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//	     }
//	 }
//
//	 private String generateHtmlReport(String siteAddress, String reportType) {
//	     Optional<Survey> surveyOptional = surveyRepository.findBySiteAddress(siteAddress);
//	     if (surveyOptional.isPresent()) {
//	         Survey survey = surveyOptional.get();
//	         List<Door> doors = survey.getDoors();
//	         Context context = new Context();
//	         context.setVariable("survey", survey);
//	         context.setVariable("doors", doors);
//	         context.setVariable("reportNumber", String.format("%05d", reportCounter.getAndIncrement()));
//	         context.setVariable("pageNumber", 1);
//
//	         if (reportType.equalsIgnoreCase("fireDoorReport")) {
//	             return templateEngine.process("report-details", context);
//	         }
//	             else if(reportType.equalsIgnoreCase("fireStoppingReport")) {
//	            	 List<FireRisk> fireRisks = survey.getFireRisks();
//		             context.setVariable("fireRisks", fireRisks);
//		             return templateEngine.process("fire-stopping", context); 
//	             
//	         } 
//	             else if(reportType.equalsIgnoreCase("fireStoppingRemedialReport")) {
//	            	 List<FireRisk> fireRisks = survey.getFireRisks();
//		             context.setVariable("fireRisks", fireRisks);
//		             List<Remediation> remediations = survey.getRemediations();
//		             context.setVariable("remediations", remediations);
//		             return templateEngine.process("fireStoppingRemedial", context); 
//	             
//	     }else if (reportType.equalsIgnoreCase("fireDoorRemedialReport")) {
//	             List<Remediation> remediations = survey.getRemediations();
//	             context.setVariable("remediations", remediations);
//	             return templateEngine.process("survey-remedial-report", context);
//	         } else {
//	             throw new IllegalArgumentException("Invalid report type: " + reportType);
//	         }
//	     } else {
//	         throw new IllegalArgumentException("Survey not found with siteAddress: " + siteAddress);
//	     }
//	 }
//
 
	 @GetMapping("/report")
	 @Operation(description ="Get api to generate survey report, survey remedial report, fireStopping report and fireStoppingRemedial report by surveyAddress and reportType")
	 public ResponseEntity<byte[]> generateReport(@RequestParam("siteAddress") String siteAddress, @RequestParam("reportType") String reportType) {
	     try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
	         String htmlContent = generateHtmlReport(siteAddress, reportType);
	         ITextRenderer renderer = new ITextRenderer();
	         renderer.setDocumentFromString(htmlContent);
	         renderer.layout();
	         renderer.createPDF(outputStream);
	         byte[] pdfBytes = outputStream.toByteArray();

	         HttpHeaders headers = new HttpHeaders();
	         headers.setContentType(MediaType.APPLICATION_PDF);
	         headers.setContentDispositionFormData("attachment", "report-details.pdf");
	         headers.setContentLength(pdfBytes.length);

	         return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
	     } catch (IOException | DocumentException e) {
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	     }
	 }
	 private String generateHtmlReport(String siteAddress, String reportType) {
		    Optional<Survey> surveyOptional = surveyRepository.findBySiteAddress(siteAddress);
		    if (surveyOptional.isPresent()) {
		        Survey survey = surveyOptional.get();
		    
		        List<Door> doors = survey.getDoors();
		        List<FireRisk> fireRisks = survey.getFireRisks();
		        List<Remediation> remediations = survey.getRemediations();
		        
		        Context context = new Context();
		        context.setVariable("survey", survey);
		        context.setVariable("doors", doors);
		        context.setVariable("fireRisks", fireRisks);
		        context.setVariable("remediations", remediations);
		        context.setVariable("reportNumber", String.format("%05d", reportCounter.getAndIncrement()));
		        context.setVariable("pageNumber", 1);

		        if (survey.getSurveyType().equalsIgnoreCase("FD") && reportType.equalsIgnoreCase("fireDoorReport")) {
		            return templateEngine.process("report-details", context);
		        } else if (survey.getSurveyType().equalsIgnoreCase("FDR") && reportType.equalsIgnoreCase("fireDoorRemedialReport")) {
		            return templateEngine.process("survey-remedial-report", context);
		        } else if (survey.getSurveyType().equalsIgnoreCase("FS") && reportType.equalsIgnoreCase("fireStoppingReport")) {
		            return templateEngine.process("fire-stopping", context);
		        } else if (survey.getSurveyType().equalsIgnoreCase("FSR") && reportType.equalsIgnoreCase("fireStoppingRemedialReport")) {
		            return templateEngine.process("fireStoppingRemedial", context);
		        } else {
		            throw new IllegalArgumentException("Invalid report type: " + reportType);
		        }
		    } else {
		        throw new IllegalArgumentException("Survey not found with siteAddress: " + siteAddress);
		    }


}
}
