package com.example.controller.admin;

import com.example.model.Role;
import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
}
