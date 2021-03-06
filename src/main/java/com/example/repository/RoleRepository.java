package com.example.repository;

import com.example.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Represent the Role Repository
 * The @Repository makes it a bean in order to be used automatically via dependency injection
 * It has already the basic query methods thanks to JpaRepository
 *
 * We just added a method to find a role by it's name
 */
@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer>
{

    Role findByRole(String role);

}
