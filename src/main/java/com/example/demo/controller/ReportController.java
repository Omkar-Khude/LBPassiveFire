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
import com.example.demo.repository.DoorRepository;
import com.example.demo.repository.SurveyRepository;
import com.lowagie.text.DocumentException;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


@RestController
@RequestMapping("/api")
public class ReportController {
	
	private AtomicInteger reportCounter = new AtomicInteger(1);
	private SurveyRepository surveyRepository;
	 private TemplateEngine templateEngine;
	 @Autowired
	 private DoorRepository doorRepository;
	 @Autowired
	    public ReportController(TemplateEngine templateEngine,SurveyRepository surveyRepository) {
	        this.templateEngine = templateEngine;
	        this.surveyRepository =surveyRepository; 
	    }
	 
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
	         Context context = new Context();
	         context.setVariable("survey", survey);
	         context.setVariable("doors", doors);
	         context.setVariable("reportNumber", String.format("%05d", reportCounter.getAndIncrement()));
	         context.setVariable("pageNumber", 1);

	         if (reportType.equalsIgnoreCase("fireDoorReport")) {
	             return templateEngine.process("report-details", context);
	         }
	             else if(reportType.equalsIgnoreCase("fireStoppingReport")) {
	            	 List<FireRisk> fireRisks = survey.getFireRisks();
		             context.setVariable("fireRisks", fireRisks);
		             return templateEngine.process("fire-stopping", context); 
	             
	         } 
	             else if(reportType.equalsIgnoreCase("fireStoppingRemedialReport")) {
	            	 List<FireRisk> fireRisks = survey.getFireRisks();
		             context.setVariable("fireRisks", fireRisks);
		             List<Remediation> remediations = survey.getRemediations();
		             context.setVariable("remediations", remediations);
		             return templateEngine.process("fireStoppingRemedial", context); 
	             
	     }else if (reportType.equalsIgnoreCase("fireDoorRemedialReport")) {
	             List<Remediation> remediations = survey.getRemediations();
	             context.setVariable("remediations", remediations);
	             return templateEngine.process("survey-remedial-report", context);
	         } else {
	             throw new IllegalArgumentException("Invalid report type: " + reportType);
	         }
	     } else {
	         throw new IllegalArgumentException("Survey not found with siteAddress: " + siteAddress);
	     }
	 }

 
	 
	 @GetMapping("/excelReportDownload")
	 @Operation(description ="Get api to download report in excel sheet")
	    public ResponseEntity<byte[]> downloadDataAsExcel() {
	        try (Workbook workbook = new XSSFWorkbook()) {
	            Sheet sheet = workbook.createSheet("Data");

	            // Fetch data from users table
	            List<Door> doors = doorRepository.findAll();
	            
	            CellStyle headerCellStyle = workbook.createCellStyle();
	            headerCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
	            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	         
	            // Write data to the Excel sheet
	            int rowNum = 0;
	            Row headerRow = sheet.createRow(rowNum++);
	            headerRow.createCell(0).setCellValue("ID No.");
	            headerRow.createCell(1).setCellValue("Block Reference");
	            headerRow.createCell(2).setCellValue("Level");
	            headerRow.createCell(3).setCellValue("Location");
	            headerRow.createCell(4).setCellValue("Fire Rating");
	            headerRow.createCell(5).setCellValue("Doorset Type");
	            headerRow.createCell(6).setCellValue("Grade Label Fitted");
	            headerRow.createCell(7).setCellValue("Remedials/ Replacement");
	            headerRow.createCell(8).setCellValue("Doorset replacement");
	            headerRow.createCell(9).setCellValue("Gaps - pads adjustment");
	            headerRow.createCell(10).setCellValue("Gaps - frame adjustment (Wedge (softwood) frame, & firestopping) Single FD30 ");
	            headerRow.createCell(11).setCellValue("Gaps - frame adjustment (Wedge (softwood) frame, & firestopping) Double FD30");
	            headerRow.createCell(12).setCellValue("Gaps - frame adjustment (Wedge (hardwood) frame, & firestopping) Single FD60");
	            headerRow.createCell(13).setCellValue("Gaps - frame adjustment (Wedge (hardwood) frame, & firestopping) Double FD60");
	            headerRow.createCell(14).setCellValue("Relip to ensure door is within gap tolerance - Side (includes removal and refitting)");
	            headerRow.createCell(15).setCellValue("Relip to ensure door is within gap tolerance - Bottom (includes removal and refitting)");
	            headerRow.createCell(16).setCellValue("gaps threshold -Surface Mounted Dropseal");
	            headerRow.createCell(17).setCellValue("gaps threshold - Internal Mounted Dropseal");
	            headerRow.createCell(18).setCellValue("Gaps Threshold - Lipping & Surface Mounted Dropseal");
	            headerRow.createCell(19).setCellValue("Install intumescent hinge pads");
	            headerRow.createCell(20).setCellValue("replace hinges");
	            headerRow.createCell(21).setCellValue("Replace 2 hinges with 3 (include rerouting)");
	            headerRow.createCell(22).setCellValue("Hinge maintenance - oiling/cleaning");
	            headerRow.createCell(23).setCellValue("Router in seals FD30");
	            headerRow.createCell(24).setCellValue("replace seals FD30");
	            headerRow.createCell(25).setCellValue("Router in seals FD60");
	            headerRow.createCell(26).setCellValue("replace seals FD60");
	            headerRow.createCell(27).setCellValue("Lock realignment");
	            headerRow.createCell(28).setCellValue("fit/replace lock");
	            headerRow.createCell(29).setCellValue("repair leaf");
	            headerRow.createCell(30).setCellValue("repair leaf Perko fill");
	            headerRow.createCell(31).setCellValue("Repair Leaf Lock Fill");
	            headerRow.createCell(32).setCellValue("repair frame Perko Fill");
	            headerRow.createCell(33).setCellValue("Softwood to fill void where locks and hinges have been removed FD30");
	            headerRow.createCell(34).setCellValue("Hardwood to fill void where locks and hinges have been removed FD60");
	            headerRow.createCell(35).setCellValue("Repair Frame");
	            headerRow.createCell(36).setCellValue("Replace FD30 Frame");
	            headerRow.createCell(37).setCellValue("Signage");
	            headerRow.createCell(38).setCellValue("lipping replacement (side)");
	            headerRow.createCell(39).setCellValue("closer adjustment");
	            headerRow.createCell(40).setCellValue("Closer replacement - Perko");
	            headerRow.createCell(41).setCellValue("closer replacement  2-4 Strength");
	            headerRow.createCell(42).setCellValue("closer replacement 3-5 Strength");
	            headerRow.createCell(43).setCellValue("bolts/bolt hole alignment etc");
	            headerRow.createCell(44).setCellValue("Firestopping behind Frame - Single");
	            headerRow.createCell(45).setCellValue("Firestopping behind Frame - Double");
	            headerRow.createCell(46).setCellValue("Handle Replacement/ Install");
	            headerRow.createCell(47).setCellValue("Handle Adjustment");
	            headerRow.createCell(48).setCellValue("Supply and Install New finger Plate 300x75");
	            headerRow.createCell(49).setCellValue("Supply and Install New Kick Plate - 900x150x1.2mm");
	            headerRow.createCell(50).setCellValue("Replacement of Letterplate with FR Letterplate FD30");
	            headerRow.createCell(51).setCellValue("Install FR Spyhole");
	            headerRow.createCell(52).setCellValue("Install Dorgard");
	            headerRow.createCell(53).setCellValue("Replace Hockey Stick Beading");
	            headerRow.createCell(54).setCellValue("Install Glazing Gasket");
	            headerRow.createCell(55).setCellValue("DamagedGlazing - Small");
	            headerRow.createCell(56).setCellValue("DamagedGlazing - Medium");
	            headerRow.createCell(57).setCellValue("DamagedGlazing - Large");
	            headerRow.createCell(58).setCellValue("Fixings Repair");
	            headerRow.createCell(59).setCellValue("Total");
	            
	            int startHeaderIndex = 8;
	            int endHeaderIndex = headerRow.getLastCellNum() - 2;
	            for (int i = startHeaderIndex; i <= endHeaderIndex; i++) {
	                Cell headerCell = headerRow.getCell(i);
	                headerCell.setCellStyle(headerCellStyle);
	            }
	            
	            for (Door door : doors) {
	                Row row = sheet.createRow(rowNum++);
	                row.createCell(0).setCellValue(door.getId());
	                row.createCell(1).setCellValue(door.getBlockReference());
	                row.createCell(2).setCellValue(door.getLevel());
	                row.createCell(3).setCellValue(door.getLocation());
	                row.createCell(4).setCellValue(door.getFireRating());
	                row.createCell(5).setCellValue("Doorset Type");
		            row.createCell(6).setCellValue(door.getLabels());
		            row.createCell(7).setCellValue(door.getDoorReplacement());
		            Cell nameCell = row.createCell(8);
		            if (door.getDoorReplacement().equalsIgnoreCase("yes")) {
		                nameCell.setCellValue("1");
		            } else if (door.getDoorReplacement().equalsIgnoreCase("no")) {
		                nameCell.setCellValue("0");
		            }
		            Cell nameCell1 = row.createCell(9);
		            if (door.getNotes().equalsIgnoreCase("Gaps - pads adjustment")) {
		                nameCell1.setCellValue("1");
		            } else{
		                nameCell1.setCellValue("0");
		            }
		            Cell nameCell2 = row.createCell(10);
		            if (door.getNotes().equalsIgnoreCase("Gaps - frame adjustment (Wedge (softwood) frame, & firestopping) Single FD30")) {
		                nameCell2.setCellValue("1");
		            } else{
		                nameCell2.setCellValue("0");
		            }
		            Cell nameCell3 = row.createCell(11);
		            if (door.getNotes().equalsIgnoreCase("Gaps - frame adjustment (Wedge (softwood) frame, & firestopping) Double FD30")) {
		                nameCell3.setCellValue("1");
		            } else{
		                nameCell3.setCellValue("0");
		            }
		            Cell nameCell4 = row.createCell(12);
		            if (door.getNotes().equalsIgnoreCase("Gaps - frame adjustment (Wedge (hardwood) frame, & firestopping) Single FD60")) {
		                nameCell4.setCellValue("1");
		            } else{
		                nameCell4.setCellValue("0");
		            }

		            Cell nameCell5 = row.createCell(13);
		            if (door.getNotes().equalsIgnoreCase("Gaps - frame adjustment (Wedge (hardwood) frame, & firestopping) Double FD60")) {
		                nameCell5.setCellValue("1");
		            } else{
		                nameCell5.setCellValue("0");
		            }

		            row.createCell(14).setCellValue(0);
		            row.createCell(15).setCellValue(0);
		            row.createCell(16).setCellValue(0);
		            row.createCell(17).setCellValue(0);
		            row.createCell(18).setCellValue(0);
		            row.createCell(19).setCellValue(0);
		            row.createCell(20).setCellValue(0);
		            row.createCell(21).setCellValue(0);
		            row.createCell(22).setCellValue(0);
		            row.createCell(23).setCellValue(0);
		            row.createCell(24).setCellValue(0);
		            row.createCell(25).setCellValue(0);
		            row.createCell(26).setCellValue(0);
		            row.createCell(27).setCellValue(0);
		            row.createCell(28).setCellValue(0);
		            row.createCell(29).setCellValue(0);
		            row.createCell(30).setCellValue(0);
		            row.createCell(31).setCellValue(0);
		            row.createCell(32).setCellValue(0);
		            row.createCell(33).setCellValue(0);
		            row.createCell(34).setCellValue(0);
		            row.createCell(35).setCellValue(0);
		            row.createCell(36).setCellValue(0);
		            row.createCell(37).setCellValue(0);
		            row.createCell(38).setCellValue(0);
		            row.createCell(39).setCellValue(0);
		            row.createCell(40).setCellValue(0);
		            row.createCell(41).setCellValue(0);
		            row.createCell(42).setCellValue(0);
		            row.createCell(43).setCellValue(0);
		            row.createCell(44).setCellValue(0);
		            row.createCell(45).setCellValue(0);
		            row.createCell(46).setCellValue(0);
		            row.createCell(47).setCellValue(0);
		            row.createCell(48).setCellValue(0);
		            row.createCell(49).setCellValue(0);
		            row.createCell(50).setCellValue(0);
		            row.createCell(51).setCellValue(0);
		            row.createCell(52).setCellValue(0);
		            row.createCell(53).setCellValue(0);
		            row.createCell(54).setCellValue(0);
		            row.createCell(55).setCellValue(0);
		            row.createCell(56).setCellValue(0);
		            row.createCell(57).setCellValue(0);
		            row.createCell(58).setCellValue(0);
		            row.createCell(59).setCellValue(0);
	            }
	            
	            rowNum++;
	            
	            
	            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
	                sheet.autoSizeColumn(i);
	            }

	            // Prepare the response
	            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	            workbook.write(outputStream);

	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	            headers.setContentDispositionFormData("attachment", "data.xlsx");
	            headers.setContentLength(outputStream.size());

	            return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
	        } catch (IOException e) {
	            // Handle exception and return appropriate response
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }
	}

