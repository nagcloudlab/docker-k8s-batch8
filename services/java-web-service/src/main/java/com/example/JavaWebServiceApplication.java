package com.example;

import java.io.File;
import java.nio.file.Files;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;


@SpringBootApplication
@RestController
public class JavaWebServiceApplication {

	String logDir = "/app/log";
	String logFileName = "java-web-service.log";

	FileWriter fileWriter;

	@PostConstruct
	public void init() {
		try {
			Files.createDirectories(new File(logDir).toPath());
			fileWriter = new FileWriter(logDir + "/" + logFileName, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping("/hello")
	public String hello() {
		// Log request to file
		try {
			fileWriter.write("Request received at " + new Date() + "\n"); 
			fileWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Hello World!";
	}

	public static void main(String[] args) {
		SpringApplication.run(JavaWebServiceApplication.class, args);
	}

}