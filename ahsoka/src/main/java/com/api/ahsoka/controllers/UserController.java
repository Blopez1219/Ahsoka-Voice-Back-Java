package com.api.ahsoka.controllers;

import com.api.ahsoka.models.UserEntity;
import com.api.ahsoka.repositories.UserRepository;
import com.api.ahsoka.request.UpdatePasswordDTO;
import com.api.ahsoka.request.UpdateUsernameDTO;
import com.api.ahsoka.services.UserDetailServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private UserDetailServiceImpl userDetailService;

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
    public UserEntity updateUsername(@Valid @RequestBody UpdateUsernameDTO updateUsernameDTO) {
        return userDetailService.updateUsername(updateUsernameDTO.getUserId(), updateUsernameDTO.getNewUsername());
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody UpdatePasswordDTO updatePasswordDTO) {
        Optional<UserEntity> userOptional = userRepository.findById(updatePasswordDTO.getUserId());
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            if (passwordEncoder.matches(updatePasswordDTO.getCurrentPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(updatePasswordDTO.getNewPassword()));
                userRepository.save(user);
                return ResponseEntity.ok("Contraseña actualizada exitosamente");
            } else {
                return ResponseEntity.badRequest().body("La contraseña actual es incorrecta");
            }
        } else {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }
    }

    @PostMapping("/uploadImage/{id}")
    public ResponseEntity<?> uploadImage(@PathVariable Long id, @RequestParam("image") MultipartFile file) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
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

            return ResponseEntity.ok("Imagen subida exitosamente");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al subir la imagen: " + e.getMessage());
        }
    }
}
