package com.example.repository;

import com.example.model.Module;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("moduleRepository")
public interface ModuleRepository extends JpaRepository<Module, Long>
{
    Module findByName(String name);

    Module findByTeacher(User teacher);
}

