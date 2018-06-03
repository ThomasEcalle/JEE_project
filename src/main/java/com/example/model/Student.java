package com.example.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 * Represent a Student in the school
 */
@Entity
@DiscriminatorValue("student")
public final class Student extends User implements Serializable
{

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.ALL
            })
    @JoinTable(name = "students_modules",
            joinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "module_id", referencedColumnName = "id")})
    private Set<Module> modules = new HashSet<>();


    public Student()
    {
    }

    public Student(String lastName, String firstName, String email)
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
