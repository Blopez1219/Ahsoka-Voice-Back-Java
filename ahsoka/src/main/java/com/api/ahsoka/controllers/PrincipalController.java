package com.api.ahsoka.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.api.ahsoka.models.UserEntity;
import com.api.ahsoka.repositories.UserRepository;
import com.api.ahsoka.request.CreateUserDTO;

import jakarta.validation.Valid;
import lombok.Builder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

@RestController
public class PrincipalController {

	@Autowired
	private PasswordEncoder	passwordEncoder;

	 @Autowired
	 private UserRepository userRepository;
	 
	@GetMapping("/hello")
	public String hello() {
		return "Hello World Not Secured";
	}
	
	@GetMapping("/helloSecured")
	public String helloSecured() {
		return "Hello World Secured";
	}

}
