package com.example.demo.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Entity.ComponentManufacturers;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.ComponentManufacturersRepository;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api")
public class ComponentManufacturersController {

	@Autowired
	private ComponentManufacturersRepository componentManufacturersRepository;
	
	@PostMapping("/addComponentManufacturersDetails")
	 @Operation(description ="Post api to add ComponentManufacturers details") 
	    public ResponseEntity<ComponentManufacturers> addComponentManufacturers(@RequestBody ComponentManufacturers componentManufacturers) {
		ComponentManufacturers savedComponentManufacturers = componentManufacturersRepository.save(componentManufacturers);
	        return ResponseEntity.ok(savedComponentManufacturers);
	    }

	@PutMapping("/editComponentManufacturersDetails/{id}")
	@Operation(description ="Put api to edit ComponentManufacturers details by id")
	public ComponentManufacturers updateComponentManufacturers(@PathVariable int id, @RequestBody ComponentManufacturers updatedComponentManufacturers) {
		ComponentManufacturers componentManufacturers = componentManufacturersRepository.findById(id).orElse(null);
	    if (componentManufacturers != null) {
	    	componentManufacturers.setName(updatedComponentManufacturers.getName());
	        return componentManufacturersRepository.save(componentManufacturers);
	    } else {
	        throw new IllegalArgumentException("ComponentManufacturers not found with id: " + id);
	    }
	}
	
	@DeleteMapping("/deleteComponentManufacturersDetails/{id}")
	@Operation(description ="Delete api to delete ComponentManufacturers details by id")
	public void deleteComponentManufacturers(@PathVariable int id) {
		componentManufacturersRepository.deleteById(id);
	}
	
	@GetMapping("/getComponentManufacturersDetails/{id}")
	@Operation(description ="Get api to get ComponentManufacturers details by id")
	public ResponseEntity<ComponentManufacturers> getComponentManufacturersById(@PathVariable int id) {
	    Optional<ComponentManufacturers> componentManufacturersOptional = componentManufacturersRepository.findById(id);
	    if (componentManufacturersOptional.isPresent()) {
	    	ComponentManufacturers componentManufacturers = componentManufacturersOptional.get();
	        return ResponseEntity.ok(componentManufacturers);
	    } else {
	    	throw new NotFoundException("componentManufacturers not found with id: " + id);
	    }
	}
	
	 @GetMapping("/getComponentManufacturersDetails")
	 @Operation(description ="Get api to get all ComponentManufacturers details") 
	 public List<ComponentManufacturers> getAllComponentManufacturers() {
	        return componentManufacturersRepository.findAll();
	 }

}
