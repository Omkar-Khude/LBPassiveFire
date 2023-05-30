package com.example.demo.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import com.example.demo.Entity.Door;
import com.example.demo.repository.DoorRepository;
import com.lowagie.text.DocumentException;


@RestController
@RequestMapping("/api")
public class ReportController {

	
	private DoorRepository doorRepository;
	
	
	 private TemplateEngine templateEngine;
	 @Autowired
	    public ReportController(TemplateEngine templateEngine, DoorRepository doorRepository) {
	        this.templateEngine = templateEngine;
	        this.doorRepository = doorRepository;
	    }
    
	@GetMapping("/report")
    public ResponseEntity<byte[]> generateReport() {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            String htmlContent = generateHtmlReport();
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
            
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    private String generateHtmlReport() {
        List<Door> doors = doorRepository.findAll();
        Context context = new Context();
        context.setVariable("doors", doors);
        return templateEngine.process("report-details", context);
    }
    
    
}
