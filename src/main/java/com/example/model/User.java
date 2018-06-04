package com.example.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance
public class User
{
    public static final String STUDENT = "Student";
    public static final String ADMIN = "Admin";
    public static final String TEACHER = "Teacher";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;
    @Column(name = "email")
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;
    @Column(name = "password")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    @Transient
    private String password;
    @Column(name = "name")
    @NotEmpty(message = "*Please provide your name")
    private String name;
    @Column(name = "last_name")
    @NotEmpty(message = "*Please provide your last name")
    private String lastName;
    @Column(name = "active")
    private int active;
    @ManyToMany()
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY,
            mappedBy = "teacher", orphanRemoval = true)
    private Set<Module> modulesTeached = new HashSet<>();


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.ALL
            })
    @JoinTable(name = "students_modules",
            joinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "module_id", referencedColumnName = "id")})
    private Set<Module> modulesAsStudent = new HashSet<>();

    public User()
    {
    }

    public User(String lastName, String firstName, String email)
    {
        this.lastName = lastName;
        this.name = firstName;
        this.email = email;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Set<Role> getRoles()
    {
        return roles;
    }

    public void setRoles(Set<Role> roles)
    {
        this.roles = roles;
    }

    public Set<Module> getModulesTeached()
    {
        return modulesTeached;
    }

    public void setModulesTeached(Set<Module> modulesTeached)
    {
        this.modulesTeached = modulesTeached;
    }

    public Set<Module> getModulesAsStudent()
    {
        return modulesAsStudent;
    }

    public void setModulesAsStudent(Set<Module> modulesAsStudent)
    {
        this.modulesAsStudent = modulesAsStudent;
    }

    public int getActive()
    {
        return active;
    }

    public void setActive(int active)
    {
        this.active = active;
    }
}
