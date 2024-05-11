package com.api.ahsoka.controllers;

import com.api.ahsoka.models.UserEntity;
import com.api.ahsoka.request.CreateUserDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.api.ahsoka.repositories.UserRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@RestController
public class AccountController {

	@Autowired
	private PasswordEncoder	passwordEncoder;

	 @Autowired
	 private UserRepository userRepository;

	@PostMapping("/createUser")
	public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO){
		String birthDateString = createUserDTO.getBirthDate();
		LocalDate birthDate = null;
		try {
			birthDate = LocalDate.parse(birthDateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		} catch (DateTimeParseException e) {
			// Devolver una respuesta de error si el formato de la fecha de nacimiento es incorrecto
			return ResponseEntity.badRequest().body("Formato de fecha de nacimiento inválido. Debe estar en formato dd/MM/yyyy.");
		}
		if(birthDate != null){
			UserEntity userEntity = UserEntity.builder()
					.email(createUserDTO.getEmail())
					.birthDate(birthDate)
					.username(createUserDTO.getUsername())
					.password(passwordEncoder.encode(createUserDTO.getPassword()))
					.build();

			// Guardar el usuario en la base de datos
			userRepository.save(userEntity);

			// Puedes devolver un ResponseEntity con un mensaje de éxito
			return ResponseEntity.ok("Usuario creado exitosamente");
		}
		return null;

	}

	

}
