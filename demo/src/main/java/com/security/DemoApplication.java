package com.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.persistence.Entity;

@SpringBootApplication(scanBasePackages="com.controller")
@EntityScan("com.model")
@EnableJpaRepositories("com.DAO")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public SecurityFilterChain secFilter(HttpSecurity httpSecurity) throws Exception{
		httpSecurity.csrf().disable().authorizeHttpRequests().anyRequest().permitAll();
		return httpSecurity.build();
	}
}
