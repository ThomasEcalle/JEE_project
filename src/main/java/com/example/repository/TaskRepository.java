package com.example.repository;

import com.example.model.Task;
import com.example.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Represent the Task Repository
 * The @Repository makes it a bean in order to be used automatically via dependency injection
 * It has already the basic query methods thanks to JpaRepository
 * <p>
 * We just added some methods
 */
@Repository("taskRepository")
public interface TaskRepository extends JpaRepository<Task, Long>
{
    /**
     * This Query is described by annotation in the Task Class
     *
     * @param creatorId
     * @param priorityLevel
     * @return List<Task>
     */
    List<Task> findByPriority(int creatorId, String priorityLevel);

    List<Task> findAllByCreator(User creator);

    List<Task> findAllByCreator(User creator, Sort sort);

    Task findById(int id);
}

