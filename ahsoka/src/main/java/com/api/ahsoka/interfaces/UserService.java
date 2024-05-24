package com.api.ahsoka.interfaces;

import com.api.ahsoka.models.UserEntity;

public interface UserService {
    public UserEntity updateUsername(String currentUsername, String newUsername);

    public UserEntity updatePassword(String username, String currentPassword, String newPassword);
}
