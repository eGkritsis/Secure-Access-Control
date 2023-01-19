package com.SpringBootSecureAccessControl.controller;

import com.SpringBootSecureAccessControl.config.MyUserDetailsService;
import com.SpringBootSecureAccessControl.config.Users;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    MyUserDetailsService myUserDetailsService;
    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession(true);

        Users user = (Users) session.getAttribute("user");

        int remainingAttempts = (Integer) session.getAttribute("remainingAttempts") + 1;


        if (!user.isAccountNonLocked()) {
            String lockedMsg = "Your account" + " (" + user.getUsername() + ") " + "has been locked due to 4 failed attempts!";
            model.addAttribute("lockedMsg", lockedMsg);
        } else {
            String remainingAttemptsMsg = "Remaining Attempts for " + user.getUsername() + " : " + remainingAttempts;
            model.addAttribute("remainingAttempts", remainingAttemptsMsg);
        }

        return "login";
    }
}
