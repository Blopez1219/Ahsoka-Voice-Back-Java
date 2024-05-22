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
}
