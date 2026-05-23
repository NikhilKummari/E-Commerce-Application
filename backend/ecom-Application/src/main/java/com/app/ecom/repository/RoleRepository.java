package com.app.ecom.repository;

import com.app.ecom.model.AppRole;
import com.app.ecom.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
     Optional<Role> findByRoleName(AppRole appRole);
}
