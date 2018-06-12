package com.example.controller.admin;

import com.example.model.Priority;
import com.example.model.Task;
import com.example.model.User;
import com.example.service.PriorityService;
import com.example.service.TaskService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

/**
 * Admnin Controller specific to the task creation
 */
@Controller
@RequestMapping("/admin")
public class AdminTaskCreationController
{

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private PriorityService priorityService;

    /**
     * Uses to show a basic home pagel
     *
     * @return ModelAndView
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home()
    {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

    /**
     * Show task's creation Form
     *
     * @return ModelAndView
     */
    @RequestMapping(value = "/createTask", method = RequestMethod.GET)
    public ModelAndView createTask()
    {
        ModelAndView modelAndView = new ModelAndView();

        List<Priority> priorities = priorityService.findAll();

        modelAndView.addObject("task", new Task());
        modelAndView.addObject("priorities", priorities);
        modelAndView.setViewName("/admin/createTask");
        return modelAndView;
    }

    /**
     * Handle task's creation result
     * Show errors or save the task in Database
     *
     * @param task
     * @param bindingResult
     * @param priorityValue
     * @return ModelAndView
     */
    @RequestMapping(value = "/createTask", method = RequestMethod.POST)
    public ModelAndView createTask(@Valid Task task, BindingResult bindingResult, String priorityValue)
    {
        ModelAndView modelAndView = new ModelAndView();

        List<Priority> priorities = priorityService.findAll();
        modelAndView.addObject("priorities", priorities);

        if (bindingResult.hasErrors())
        {
            modelAndView.setViewName("/admin/createTask");
            return modelAndView;
        }
        for (Priority p : priorities)
        {
            if (p.getLevel().equals(priorityValue))
            {
                task.setPriority(p);
                priorityService.save(p);
                break;
            }
        }

        if (task.getPriority() == null)
        {
            modelAndView.addObject("error", "Please enter a correct priority");

            modelAndView.setViewName("/admin/createTask");
            return modelAndView;
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByEmail(email);

        task.setCreator(user);
        user.getTasks().add(task);

        taskService.save(task);
        userService.updateUser(user);


        modelAndView.addObject("successMessage", "Task has been registered successfully !");
        modelAndView.addObject("task", new Task());
        modelAndView.setViewName("/admin/createTask");


        return modelAndView;
    }


}
