package com.example.service;

import com.example.model.User;

import java.util.Set;

public interface UserService
{
    User findUserByEmail(String email);

    Set<User> findAllByRolesContaining(String rolename);

    User findById(int id);

    void deleteUser(User user);

    void saveStudent(User user);

    void saveAdmin(User user);

    void saveTeacher(User user);
}
