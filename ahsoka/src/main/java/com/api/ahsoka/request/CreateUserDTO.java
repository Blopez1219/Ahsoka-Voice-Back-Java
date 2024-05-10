package com.api.ahsoka.request;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {
	
	@Email
	@NotBlank
	private String email;

	@NotNull(message = "La fecha de nacimiento no puede ser nula")
	private String birthDate;

	@NotBlank
	private String username;

	@NotBlank
	private String password;

	private String image;
}
