package com.example.repository;

import com.example.model.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Represent the Priority Repository
 * The @Repository makes it a bean in order to be used automatically via dependency injection
 * It has already the basic query methods thanks to JpaRepository
 */
@Repository("priorityRepository")
public interface PriorityRepository extends JpaRepository<Priority, Long>
{

}

