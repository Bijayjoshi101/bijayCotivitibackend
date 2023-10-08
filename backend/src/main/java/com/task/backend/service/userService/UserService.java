package com.task.backend.service.userService;

import com.task.backend.dto.UserDto;
import com.task.backend.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(UserDto userDto);

    Optional<User> findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
