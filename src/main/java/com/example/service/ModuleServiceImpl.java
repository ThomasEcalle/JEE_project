package com.example.service;

import com.example.model.Module;
import com.example.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("moduleService")
public class ModuleServiceImpl implements ModuleService
{
    @Autowired
    private ModuleRepository moduleRepository;

    @Override
    public void deleteModule(Module module)
    {
        moduleRepository.delete(module);
    }
}
