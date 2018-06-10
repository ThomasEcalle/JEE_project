package com.example.service;

import com.example.model.User;

import java.util.Set;

/**
 * Represent all the methods to be implemented by the USer Service
 */
public interface UserService
{
    User findUserByEmail(String email);

    Set<User> findAllByRolesContaining(String roleName);

    User findById(int id);

    void deleteUser(User user);

    void saveUser(User user);

    void updateUser(User user);


}
