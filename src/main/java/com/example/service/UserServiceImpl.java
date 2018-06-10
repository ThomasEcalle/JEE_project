package com.example.service;

import com.example.model.Role;
import com.example.model.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of the UserService
 * The @Service annotation enable it to be used as a Bean
 */
@Service("userService")
public class UserServiceImpl implements UserService
{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findUserByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

    @Override
    public Set<User> findAllByRolesContaining(String roleName)
    {
        return userRepository.findAllByRolesContaining(roleName);
    }

    @Override
    public User findById(int id)
    {
        return userRepository.findById(id);
    }

    @Override
    public void deleteUser(User user)
    {
        userRepository.delete(user);
    }

    /**
     * For the example, we always save the User with Admin role
     * But, in the future, we can easily handle different roles
     *
     * @param user
     */
    @Override
    public void saveUser(User user)
    {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole(User.ADMIN);
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user)
    {
        userRepository.save(user);
    }

}
