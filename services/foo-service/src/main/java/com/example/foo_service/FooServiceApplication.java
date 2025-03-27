package com.example.foo_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class FooServiceApplication {

	RestTemplate restTemplate = new RestTemplate();

	@GetMapping("/foo")
	public String foo() {
		String barResponse=restTemplate.getForObject("http://bar-service:8080/bar", String.class);
		return "foo -> "+barResponse;
	}

	public static void main(String[] args) {
		SpringApplication.run(FooServiceApplication.class, args);
	}

}
