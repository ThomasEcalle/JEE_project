package com.example.service;

import com.example.model.Priority;
import com.example.repository.PriorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the PriorityService
 * The @Service annoation enable it to be used as a Bean
 */
@Service("priorityService")
public class PriorityServiceImpl implements PriorityService
{
    @Autowired
    private PriorityRepository priorityRepository;

    @Override
    public List<Priority> findAll()
    {
        return priorityRepository.findAll();
    }

    @Override
    public void save(Priority priority)
    {
        priorityRepository.save(priority);
    }


}
