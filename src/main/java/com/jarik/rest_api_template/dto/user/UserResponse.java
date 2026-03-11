package com.jarik.rest_api_template.dto.user;

import java.util.List;

public record UserResponse(
        Long id,
        String name,
        String lastName,
        Integer age,
        String email,
        String username,
        List<String> roles
) {
}
