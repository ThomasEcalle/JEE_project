package com.example.service;

import com.example.model.Task;
import com.example.model.User;
import com.example.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the TaskService
 * The @Service annotation enable it to be used as a Bean
 */
@Service("taskService")
public class TaskServiceImpl implements TaskService
{
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void deleteTask(Task task)
    {
        taskRepository.delete(task);
    }

    @Override
    public Task findById(int id)
    {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> findAllByCreator(User creator, Sort sort)
    {
        return taskRepository.findAllByCreator(creator, sort);
    }

    @Override
    public List<Task> findAllByCreator(User creator)
    {
        return taskRepository.findAllByCreator(creator);
    }

    @Override
    public List<Task> findByPriority(int creatorId, String priorityLevel)
    {
        return taskRepository.findByPriority(creatorId, priorityLevel);
    }

    @Override
    public void save(Task task)
    {
        taskRepository.save(task);
    }


}
