package com.example.service;

import com.example.model.User;

public interface UserService
{
    public User findUserByEmail(String email);

    public void saveStudent(User user);

    public void saveAdmin(User user);

    public void saveTeacher(User user);
}
