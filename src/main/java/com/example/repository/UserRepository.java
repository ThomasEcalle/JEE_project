package com.example.repository;

import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Represent the User Repository
 * The @Repository makes it a bean in order to be used automatically via dependency injection
 * It has already the basic query methods thanks to JpaRepository
 * <p>
 * We just added some methods
 */
@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long>
{
    User findByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.role=:rolename")
    Set<User> findAllByRolesContaining(@Param("rolename") String rolename);

    User findById(int id);
}
