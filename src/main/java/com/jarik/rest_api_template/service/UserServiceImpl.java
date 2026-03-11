package com.jarik.rest_api_template.service;

import com.jarik.rest_api_template.dto.user.CreateUserRequest;
import com.jarik.rest_api_template.dto.user.UpdateUserRequest;
import com.jarik.rest_api_template.exception.DuplicateResourceException;
import com.jarik.rest_api_template.exception.ResourceNotFoundException;
import com.jarik.rest_api_template.model.User;
import com.jarik.rest_api_template.repository.UserRepository;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User createUser(CreateUserRequest request) {
        validateUniqueFields(request.username(), request.email(), null);

        User user = new User();
        applyRequest(user, request.name(), request.lastName(), request.age(), request.email(), request.username(),
                request.password(), request.roleIds(), true);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(Long id, UpdateUserRequest request) {
        User persisted = getUserById(id);
        validateUniqueFields(request.username(), request.email(), persisted.getId());

        applyRequest(persisted, request.name(), request.lastName(), request.age(), request.email(), request.username(),
                request.password(), request.roleIds(), false);
        return persisted;
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id %d was not found".formatted(id)));
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with id %d was not found".formatted(id));
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with username %s was not found".formatted(username)));
    }

    private void applyRequest(User user,
                              String name,
                              String lastName,
                              Integer age,
                              String email,
                              String username,
                              String rawPassword,
                              List<Long> roleIds,
                              boolean passwordRequired) {
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);
        user.setEmail(email);
        user.setUsername(username);
        user.setRoles(roleService.getRolesByIds(roleIds));

        if (rawPassword != null && !rawPassword.isBlank()) {
            user.setPassword(passwordEncoder.encode(rawPassword));
            return;
        }

        if (passwordRequired) {
            throw new IllegalArgumentException("Password is required");
        }
    }

    private void validateUniqueFields(String username, String email, Long existingUserId) {
        userRepository.findByUsername(username)
                .filter(user -> !user.getId().equals(existingUserId))
                .ifPresent(user -> {
                    throw new DuplicateResourceException("Username is already in use");
                });

        userRepository.findByEmail(email)
                .filter(user -> !user.getId().equals(existingUserId))
                .ifPresent(user -> {
                    throw new DuplicateResourceException("Email is already in use");
                });
    }
}
