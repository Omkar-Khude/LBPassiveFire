package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
		info=@Info(
				title="LB Passive Fire"
				))
public class LBpassiveFireApplication {

	public static void main(String[] args) {
		SpringApplication.run(LBpassiveFireApplication.class, args);
	}

}
