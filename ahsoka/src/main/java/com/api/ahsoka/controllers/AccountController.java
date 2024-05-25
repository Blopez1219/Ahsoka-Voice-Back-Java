package com.api.ahsoka.controllers;

import com.api.ahsoka.models.UserEntity;
import com.api.ahsoka.request.CreateUserDTO;
import com.api.ahsoka.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
			ApiResponse response = new ApiResponse(HttpStatus.BAD_REQUEST.value(), "Formato de fecha de nacimiento inválido. Debe estar en formato dd/MM/yyyy.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		if (userRepository.findByEmail(createUserDTO.getEmail()).isPresent()) {
			ApiResponse response = new ApiResponse(HttpStatus.BAD_REQUEST.value(), "El correo electrónico ya está registrado.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		// Verificar si el nombre de usuario ya existe
		if (userRepository.findByUsername(createUserDTO.getUsername()).isPresent()) {
			ApiResponse response = new ApiResponse(HttpStatus.BAD_REQUEST.value(), "El nombre de usuario ya está registrado.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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
			ApiResponse response = new ApiResponse(HttpStatus.OK.value(), "Usuario creado exitosamente");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		return null;

	}

	

}
