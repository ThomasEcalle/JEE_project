package com.example.service;

import com.example.model.Role;
import com.example.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleServiceImpl implements RoleService
{
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void deleteRole(Role role)
    {
        roleRepository.delete(role);
    }
}
