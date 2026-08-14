package com.example.students;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableCaching
@EnableAsync
@SpringBootApplication
public class StudentsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentsBackendApplication.class, args);
	}

}
