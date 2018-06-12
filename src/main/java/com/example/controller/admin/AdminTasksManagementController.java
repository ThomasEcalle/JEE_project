package com.example.controller.admin;

import com.example.model.Priority;
import com.example.model.Task;
import com.example.model.User;
import com.example.service.PriorityService;
import com.example.service.TaskService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Admin controller specific to the tasks displaying
 */
@Controller
@RequestMapping("/admin")
public class AdminTasksManagementController
{

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private PriorityService priorityService;

    /**
     * Show the list of tasks
     * May handle a sort param like "name" or "priority"
     *
     * @param sort
     * @param info
     * @return ModelAndView
     */
    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public ModelAndView tasks(@RequestParam(name = "sort", required = false) String sort, @RequestParam(name = "info", required = false) String info)
    {
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        List<Task> tasks = new ArrayList<>();

        if (sort != null)
        {
            if ("name".equals(sort))
            {
                tasks = taskService.findAllByCreatorSortedByName(user.getId());
            }
            else if ("priority".equals(sort))
            {
                tasks = taskService.findAllByCreator(user, new Sort(Sort.Direction.ASC, "priority"));
            }
        }
        else
        {
            tasks = taskService.findAllByCreator(user);
        }


        if (info != null && !info.isEmpty())
        {
            modelAndView.addObject("info", info);
        }

        modelAndView.addObject("tasks", tasks);
        modelAndView.setViewName("admin/tasks");
        return modelAndView;
    }

    /**
     * Display all the "High" level priority tasks in a list
     *
     * @return ModelAndView
     */
    @RequestMapping(value = "/urgencies", method = RequestMethod.GET)
    public ModelAndView urgencies()
    {
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        List<Task> tasks = taskService.findByPriority(user.getId(), "High");

        modelAndView.addObject("tasks", tasks);
        modelAndView.setViewName("admin/urgencies");
        return modelAndView;
    }

    /**
     * Delete a task then redirect to tasks' list
     *
     * @param id
     * @return String
     */
    @RequestMapping(value = "/tasks/delete", method = RequestMethod.GET)
    public String handleDeleteTask(@RequestParam(name = "id") String id)
    {

        System.out.println("Starting deleting task with id : " + id);

        Task task = taskService.findById(Integer.parseInt(id));

        taskService.deleteTask(task);

        return "redirect:/admin/tasks?info=Task correctly deleted";
    }

    /**
     * Display the "update task" Form
     *
     * @param id
     * @return ModelAndView
     */
    @RequestMapping(value = "/task/update", method = RequestMethod.GET)
    public ModelAndView updateTask(@RequestParam(name = "id") String id)
    {
        ModelAndView modelAndView = new ModelAndView();

        List<Priority> priorities = priorityService.findAll();

        Task task = taskService.findById(Integer.parseInt(id));

        modelAndView.addObject("task", task);
        modelAndView.addObject("priorities", priorities);

        modelAndView.setViewName("admin/update");

        return modelAndView;
    }

    /**
     * Handle a task's update form result by displaying errors or uptading task in Database
     *
     * @param id
     * @param task
     * @param bindingResult
     * @param priorityValue
     * @return ModelAndView
     */
    @RequestMapping(value = "/task/update/{id}", method = RequestMethod.POST)
    public ModelAndView updateUser(@PathVariable(value = "id") String id, @Valid Task task, BindingResult bindingResult, String priorityValue)
    {
        ModelAndView modelAndView = new ModelAndView();

        List<Priority> priorities = priorityService.findAll();
        modelAndView.addObject("priorities", priorities);

        if (bindingResult.hasErrors())
        {
            modelAndView.setViewName("/admin/update");
            return modelAndView;
        }
        else
        {
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
                modelAndView.addObject("priorityError", "Please enter a correct priority");

                modelAndView.setViewName("/admin/update");
                return modelAndView;
            }

            Task oldTask = taskService.findById(Integer.parseInt(id));

            task.setCreator(oldTask.getCreator());
            taskService.save(task);


            modelAndView.setViewName("redirect:/admin/tasks?info=Task correctly updated");

        }


        return modelAndView;
    }

}
