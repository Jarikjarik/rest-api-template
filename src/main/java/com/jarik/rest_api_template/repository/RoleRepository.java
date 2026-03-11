package com.jarik.rest_api_template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jarik.rest_api_template.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
