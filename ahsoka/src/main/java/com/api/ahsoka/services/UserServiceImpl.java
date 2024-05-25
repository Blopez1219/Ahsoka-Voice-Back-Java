package com.api.ahsoka.services;

import com.api.ahsoka.exceptions.Exceptions;
import com.api.ahsoka.interfaces.UserService;
import com.api.ahsoka.models.UserEntity;
import com.api.ahsoka.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity updateUsername(String currentUsername, String newUsername) {
        // Verificar si el nuevo nombre de usuario ya existe
        Optional<UserEntity> existingUser = userRepository.findByUsername(newUsername);
        if (existingUser.isPresent()) {
            throw new Exceptions.UsernameAlreadyExistsException("El usuario " + newUsername + " ya existe");
        }

        // Lógica para actualizar el nombre de usuario
        Optional<UserEntity> user = userRepository.findByUsername(currentUsername);
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            userEntity.setUsername(newUsername);
            userRepository.save(userEntity);
            return userEntity;
        } else {
            throw new UsernameNotFoundException("El usuario " + currentUsername + " no existe");
        }
    }

    public UserEntity updatePassword(String username, String currentPassword, String newPassword) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            UserEntity userEntity = userOptional.get();

            if (passwordEncoder.matches(currentPassword, userEntity.getPassword())) {
                if(!passwordEncoder.matches(newPassword, userEntity.getPassword())){
                    userEntity.setPassword(passwordEncoder.encode(newPassword)); // Asegúrate de encriptar la nueva contraseña si es necesario
                    userRepository.save(userEntity);
                    return userEntity;
                }else {
                    throw new IllegalArgumentException("La nueva contraseña tiene que ser diferente a la contraseña actual");
                }

            } else {
                throw new IllegalArgumentException("La contraseña actual no es válida");
            }
        } else {
            throw new UsernameNotFoundException("El usuario " + username + " no existe");
        }
    }

}
