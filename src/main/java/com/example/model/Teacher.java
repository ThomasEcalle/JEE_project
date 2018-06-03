package com.example.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 * Represent a Student in the school
 */
@Entity
@DiscriminatorValue("teacher")
public final class Teacher extends User implements Serializable
{

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "teacher")
    private Set<Module> modules = new HashSet<>();


    public Teacher()
    {
    }

    public Teacher(String lastName, String firstName, String email)
    {
        super(lastName, firstName, email);
    }

    public Set<Module> getModules()
    {
        return modules;
    }

    public void setModules(Set<Module> modules)
    {
        this.modules = modules;
    }
}
