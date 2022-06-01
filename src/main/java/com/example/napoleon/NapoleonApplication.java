package com.example.napoleon;

import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@RestController
@EnableJpaRepositories
public class NapoleonApplication {

	public static void main(String[] args) {
		SpringApplication.run(NapoleonApplication.class, args);
	}

	@Configuration
	@EnableWebMvc
	@EnableScheduling
	public class WebConfig implements WebMvcConfigurer {
		@PostConstruct
		public void init() {
			TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta")); // It will set UTC timezone
			System.out.println("Spring boot application running in UTC timezone :" + new Date()); // It will print UTC
																									// timezone
		}

		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**").allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
		}
	}

	@GetMapping("/")
	public String hello() {
		return "Hello Spring Boot Oiiii!";
	}
}
