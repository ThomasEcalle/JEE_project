package com.example.controller.admin;

import com.example.model.Role;
import com.example.model.User;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminUserCreationController
{

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home()
    {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.GET)
    public ModelAndView createUser()
    {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();

        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new Role(User.ADMIN));
        roles.add(new Role(User.TEACHER));
        roles.add(new Role(User.STUDENT));

        modelAndView.addObject("user", user);
        modelAndView.addObject("roles", roles);
        modelAndView.setViewName("/admin/createUser");
        return modelAndView;
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public ModelAndView createUser(@Valid User user, BindingResult bindingResult, String role)
    {
        ModelAndView modelAndView = new ModelAndView();

        User userExists = userService.findUserByEmail(user.getEmail());
        ArrayList<Role> exampleRoles = new ArrayList<>();
        exampleRoles.add(new Role(User.ADMIN));
        exampleRoles.add(new Role(User.TEACHER));
        exampleRoles.add(new Role(User.STUDENT));

        modelAndView.addObject("roles", exampleRoles);

        Set<Role> roles = new HashSet<>();
        if (userExists != null)
        {
            bindingResult.rejectValue("email", "error.user", "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors())
        {
            modelAndView.setViewName("/admin/createUser");
        }
        else
        {
            switch (role)
            {
                case User.ADMIN:
                    userService.saveAdmin(user);
                    break;
                case User.TEACHER:
                    userService.saveTeacher(user);
                    break;
                case User.STUDENT:
                    userService.saveStudent(user);
                    break;
                default:
                    modelAndView.addObject("error", true);
                    modelAndView.setViewName("/admin/createUser");
                    return modelAndView;
            }
            modelAndView.addObject("successMessage", "User " + user.getName() + " has been registered successfully as a " + role);
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("/admin/createUser");
        }

        return modelAndView;
    }


}
