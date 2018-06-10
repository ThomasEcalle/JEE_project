package com.example.service;

import com.example.model.Priority;

import java.util.List;

/**
 * Represent all the methods to be implemented by the Priority Service
 */
public interface PriorityService
{
    List<Priority> findAll();

    void save(Priority priority);
}
