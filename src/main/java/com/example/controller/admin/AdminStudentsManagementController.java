package com.example.controller.admin;

import com.example.model.Role;
import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminStudentsManagementController
{

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public ModelAndView home(@RequestParam(name = "info", required = false) String info)
    {
        ModelAndView modelAndView = new ModelAndView();
        HashSet<Role> roles = new HashSet<>();
        roles.add(new Role(User.STUDENT));
        Set<User> students = userService.findAllByRolesContaining(User.STUDENT);

        if (info != null && !info.isEmpty())
        {
            modelAndView.addObject("info", info);
        }

        modelAndView.addObject("students", students);
        modelAndView.setViewName("admin/students");
        return modelAndView;
    }

    @RequestMapping(value = "/students/delete", method = RequestMethod.GET)
    public String handleDeleteStudent(@RequestParam(name = "id") String id)
    {
        System.out.println("Starting deleting user with id : " + id);

        User user = userService.findById(Integer.parseInt(id));

        String name = user.getName();

        userService.deleteUser(user);

        return "redirect:/admin/students?info=User " + name + " correctly deleted";
    }

    @RequestMapping(value = "/user/update", method = RequestMethod.GET)
    public ModelAndView updateUser(@RequestParam(name = "id") String id)
    {
        ModelAndView modelAndView = new ModelAndView();
        ArrayList<Role> exampleRoles = new ArrayList<>();
        exampleRoles.add(new Role(User.ADMIN));
        exampleRoles.add(new Role(User.TEACHER));
        exampleRoles.add(new Role(User.STUDENT));


        User user = userService.findById(Integer.parseInt(id));

        modelAndView.addObject("user", user);
        modelAndView.addObject("roles", exampleRoles);
        modelAndView.setViewName("admin/update");
        return modelAndView;
    }

    @RequestMapping(value = "/user/update/{id}", method = RequestMethod.POST)
    public ModelAndView updateUser(@PathVariable(value = "id") String id, User user, BindingResult bindingResult, String role)
    {
        ModelAndView modelAndView = new ModelAndView();

        User userExists = userService.findUserByEmail(user.getEmail());

        ArrayList<Role> exampleRoles = new ArrayList<>();
        exampleRoles.add(new Role(User.ADMIN));
        exampleRoles.add(new Role(User.TEACHER));
        exampleRoles.add(new Role(User.STUDENT));

        modelAndView.addObject("roles", exampleRoles);

        int oldId = Integer.parseInt(id);

        if (userExists != null && oldId != userExists.getId())
        {
            bindingResult.rejectValue("email", "error.user", "There is already a user registered with the email provided");
        }

        if (userService == null)
        {
            userExists = userService.findById(oldId);

        }
        else if (oldId == userExists.getId())
        {

        }

        else
        {
            if (User.ADMIN.equals(role))
            {
                userService.saveAdmin(user);

            }
            else if (User.TEACHER.equals(role))
            {
                userService.saveTeacher(user);

            }
            else if (User.STUDENT.equals(role))
            {
                userService.saveStudent(user);
            }
            else
            {
                modelAndView.addObject("error", true);
                modelAndView.setViewName("admin/update");
                return modelAndView;
            }
            modelAndView.addObject("successMessage", "User " + user.getName() + " has been updated successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("admin/update");
        }

        return modelAndView;
    }

}
