package com.jarik.rest_api_template.service;

import com.jarik.rest_api_template.exception.ResourceNotFoundException;
import com.jarik.rest_api_template.model.Role;
import com.jarik.rest_api_template.repository.RoleRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Role> getRolesByIds(List<Long> ids) {
        Set<Role> roles = new HashSet<>(roleRepository.findAllById(ids));
        if (roles.size() != ids.size()) {
            throw new ResourceNotFoundException("One or more roles were not found");
        }
        return roles;
    }
}
