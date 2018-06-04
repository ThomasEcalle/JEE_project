package com.example.configuration;

import com.example.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler
{
    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response)
    {
        // Get the role of logged in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString();

        String targetUrl = "";
        if (role.contains(User.ADMIN))
        {
            targetUrl = "/admin/home";
        }
        else if (role.contains(User.TEACHER))
        {
            targetUrl = "/teacher/home";
        }
        else
        {
            targetUrl = "/student/home";
        }
        return targetUrl;
    }
}