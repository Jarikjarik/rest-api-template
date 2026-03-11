package com.jarik.rest_api_template.mapper;

import com.jarik.rest_api_template.dto.user.UserResponse;
import com.jarik.rest_api_template.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getAge(),
                user.getEmail(),
                user.getUsername(),
                user.getRolesSorted().stream()
                        .map(role -> role.getName().replace("ROLE_", ""))
                        .toList()
        );
    }
}
