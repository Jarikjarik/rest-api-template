package com.jarik.rest_api_template.controller;

import com.jarik.rest_api_template.dto.user.CreateUserRequest;
import com.jarik.rest_api_template.dto.user.UpdateUserRequest;
import com.jarik.rest_api_template.dto.user.UserResponse;
import com.jarik.rest_api_template.mapper.UserMapper;
import com.jarik.rest_api_template.model.User;
import com.jarik.rest_api_template.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/users")
@Tag(name = "Users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    @Operation(summary = "Return all users")
    public List<UserResponse> getUsers() {
        return userService.getAllUsers().stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Return a user by id")
    public UserResponse getUser(@PathVariable Long id) {
        return userMapper.toResponse(userService.getUserById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new user")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        User createdUser = userService.createUser(request);
        UserResponse response = userMapper.toResponse(createdUser);

        return ResponseEntity
                .created(URI.create("/api/admin/users/" + createdUser.getId()))
                .body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing user")
    public UserResponse updateUser(@PathVariable Long id,
                                   @Valid @RequestBody UpdateUserRequest request) {
        return userMapper.toResponse(userService.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user by id")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
