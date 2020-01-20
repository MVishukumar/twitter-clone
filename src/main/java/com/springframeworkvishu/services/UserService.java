package com.springframeworkvishu.services;

import com.springframeworkvishu.command.UserCommand;

public interface UserService {
    UserCommand createNewUser(UserCommand userCommand);
    UserCommand editPassword(Long id, String newPassword);
    void deleteUser(Long id);
    UserCommand findById(Long id);
    UserCommand findByUsername(String username);
}
