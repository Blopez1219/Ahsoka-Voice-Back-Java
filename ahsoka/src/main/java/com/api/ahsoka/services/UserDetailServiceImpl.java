package com.api.ahsoka.services;

import com.api.ahsoka.models.UserEntity;
import com.api.ahsoka.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("El usuario "+username+" no existe"));


        return new User(userEntity.getUsername(), userEntity.getPassword(), true, true, true, true, Collections.emptyList() );
    }

    public UserEntity updateUsername(Long id, String newUsername) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            user.setUsername(newUsername);
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with id " + id);
        }
    }
}
