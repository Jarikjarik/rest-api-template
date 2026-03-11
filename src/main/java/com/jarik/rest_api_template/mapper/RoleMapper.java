package com.jarik.rest_api_template.mapper;

import com.jarik.rest_api_template.dto.role.RoleResponse;
import com.jarik.rest_api_template.model.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleResponse toResponse(Role role) {
        return new RoleResponse(role.getId(), role.getName().replace("ROLE_", ""));
    }
}
