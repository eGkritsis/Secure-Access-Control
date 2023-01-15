package com.aueb.springloginsecurity;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {
    @Autowired
    UserRepository userRepository;
    @GetMapping("/login")
    public String login(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        int remainingAttempts = userRepository.findRemainingAttemptsByUsername(username);
        //int remainingAttempts = (Integer) request.getAttribute("remainingAttempts");
        //model.addAttribute("remainingAttempts", remainingAttempts);
        //String test = "Hello World";

        model.addAttribute("remainingAttempts", remainingAttempts);
        return "login";
    }
}
