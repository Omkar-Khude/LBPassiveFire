package com.example.demo.controller;

import com.lowagie.text.DocumentException;

import io.swagger.v3.oas.annotations.Operation;

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
import java.io.ByteArrayOutputStream;
import java.io.IOException;


@RestController
@RequestMapping("/api")
public class WeddingCardController {
	
	 private TemplateEngine templateEngine;
	 @Autowired
	    public WeddingCardController(TemplateEngine templateEngine) {
	        this.templateEngine = templateEngine;
	 }

	@GetMapping("/generateWeddingCard")
	@Operation(description = "Get API to generate wedding card")
	public ResponseEntity<byte[]> generatePdf(
	    @RequestParam("invitation line 1") String input1,
	    @RequestParam("bride & groom name") String input2,
	    @RequestParam("invitation line 2") String input3,
	    @RequestParam("marriage date") String input4,
	    @RequestParam("marriage venue") String input5,
	    @RequestParam("special message") String input6,
	    @RequestParam("templateNumber") int templateNumber
	    
	) {
	    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
	        String htmlContent = generateHtmlReport(input1, input2, input3, input4, input5, input6, templateNumber);
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

	private String generateHtmlReport(String input1, String input2, String input3, String input4, String input5, String input6, int templateNumber) {
	    String templateName;
	    if (templateNumber == 1) {
	        templateName = "1.html";
	    } else if (templateNumber == 2) {
	        templateName = "2.html";
	    } else if(templateNumber == 3) {
	        templateName = "3.html";
	    }else if(templateNumber == 4) {
	        templateName = "4.html";}
	    else if(templateNumber == 5) {
	        templateName = "5.html";}
	    else if(templateNumber == 6) {
	        templateName = "6.html";
	    }else{
	    
	        throw new IllegalArgumentException("Invalid template number: " + templateNumber);
	    }

	    Context context = new Context();
	    context.setVariable("input1", input1);
	    context.setVariable("input2", input2);
	    context.setVariable("input3", input3);
	    context.setVariable("input4", input4);
	    context.setVariable("input5", input5);
	    context.setVariable("input6", input6);
	    

	    return templateEngine.process(templateName, context);
	}

    }