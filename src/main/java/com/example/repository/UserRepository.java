package com.example.repository;

import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long>
{
    User findByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.role=:rolename")
    Set<User> findAllByRolesContaining(@Param("rolename") String rolename);

    User findById(int id);
}
