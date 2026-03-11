package com.jarik.rest_api_template.controller;

import com.jarik.rest_api_template.dto.role.RoleResponse;
import com.jarik.rest_api_template.mapper.RoleMapper;
import com.jarik.rest_api_template.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/roles")
@Tag(name = "Roles")
public class RoleController {

    private final RoleService roleService;
    private final RoleMapper roleMapper;

    public RoleController(RoleService roleService, RoleMapper roleMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }

    @GetMapping
    @Operation(summary = "Return all available roles")
    public List<RoleResponse> getRoles() {
        return roleService.getAllRoles().stream()
                .map(roleMapper::toResponse)
                .toList();
    }
}
