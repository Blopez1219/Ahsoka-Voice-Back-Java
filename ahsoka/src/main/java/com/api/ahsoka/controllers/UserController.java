package com.api.ahsoka.controllers;

import com.api.ahsoka.exceptions.Exceptions;
import com.api.ahsoka.models.UserEntity;
import com.api.ahsoka.repositories.UserRepository;
import com.api.ahsoka.request.UpdatePasswordDTO;
import com.api.ahsoka.request.UpdateUsernameDTO;
import com.api.ahsoka.response.ApiResponse;
import com.api.ahsoka.services.UserDetailServiceImpl;
import com.api.ahsoka.services.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    private static String UPLOAD_DIR = "uploads/";

    @GetMapping("/hello")
    public String hello() {
        return "Hello, your token is valid!";
    }

    @PutMapping("/updateUsername")
    public ResponseEntity<ApiResponse> updateUsername(@Valid @RequestBody UpdateUsernameDTO updateUsernameDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        try {
            userService.updateUsername(currentUsername, updateUsernameDTO.getNewUsername());
            ApiResponse response = new ApiResponse(HttpStatus.OK.value(), "Nombre de usuario actualizado correctamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exceptions.UsernameAlreadyExistsException e) {
            ApiResponse response = new ApiResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exceptions.UsernameNotFoundException e) {
            ApiResponse response = new ApiResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody UpdatePasswordDTO updatePasswordDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        try {
            userService.updatePassword(currentUsername, updatePasswordDTO.getCurrentPassword(), updatePasswordDTO.getNewPassword());
            ApiResponse response = new ApiResponse(HttpStatus.OK.value(), "Contraseña actualizada correctamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse response = new ApiResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exceptions.UsernameNotFoundException e) {
            ApiResponse response = new ApiResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/uploadImage/{id}")
    public ResponseEntity<?> uploadImage(@PathVariable Long id, @RequestParam("image") MultipartFile file) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            ApiResponse response = new ApiResponse(HttpStatus.BAD_REQUEST.value(), "Usuario no encontrado");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        UserEntity user = userOptional.get();

        // Crear el directorio si no existe
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdir();
        }

        try {
            // Guardar la imagen en el servidor
            String fileName = id + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, fileName);
            Files.write(filePath, file.getBytes());

            // Actualizar la ruta de la imagen en el usuario
            user.setImage(filePath.toString());
            userRepository.save(user);
            ApiResponse response = new ApiResponse(HttpStatus.OK.value(), "Imagen subida exitosamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            ApiResponse response = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al subir la imagen: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getImage/{id}")
    public ResponseEntity<?> getImage(@PathVariable Long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            ApiResponse response = new ApiResponse(HttpStatus.NOT_FOUND.value(), "Usuario no encontrado");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        UserEntity user = userOptional.get();
        String imagePath = user.getImage();
        if (imagePath == null) {
            ApiResponse response = new ApiResponse(HttpStatus.NOT_FOUND.value(), "Imagen no encontrada");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            Path filePath = Paths.get(imagePath);
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                ApiResponse response = new ApiResponse(HttpStatus.NOT_FOUND.value(), "Imagen no encontrada o no es legible");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al obtener la imagen: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
