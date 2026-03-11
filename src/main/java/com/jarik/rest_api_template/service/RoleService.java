package com.jarik.rest_api_template.service;

import com.jarik.rest_api_template.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {

    List<Role> getAllRoles();

    Set<Role> getRolesByIds(List<Long> ids);
}
