package com.example.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Represent the Priority Entity in Database
 * A priority is associated to a Task
 */
@Entity
public final class Priority
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String level = "Medium";

    public Priority()
    {
    }

    public Priority(String level)
    {
        this.level = level;
    }

    public String getLevel()
    {
        return level;
    }

    public void setLevel(String level)
    {
        this.level = level;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

}
