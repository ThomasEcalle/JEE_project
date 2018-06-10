package com.example.service;

import com.example.model.Task;
import com.example.model.User;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Represent all the methods to be implemented by the Task Service
 */
public interface TaskService
{
    void deleteTask(Task task);

    Task findById(int id);

    List<Task> findAllByCreatorSortedByName(int id);

    List<Task> findAllByCreator(User creator, Sort sort);

    List<Task> findAllByCreator(User creator);

    List<Task> findByPriority(int creatorId, String priorityLevel);

    void save(Task task);

}
