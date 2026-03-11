package com.jarik.rest_api_template.service;

import com.jarik.rest_api_template.dto.user.CreateUserRequest;
import com.jarik.rest_api_template.dto.user.UpdateUserRequest;
import com.jarik.rest_api_template.model.User;
import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User createUser(CreateUserRequest request);

    User updateUser(Long id, UpdateUserRequest request);

    User getUserById(Long id);

    void deleteUserById(Long id);

    User findByUsername(String username);
}
