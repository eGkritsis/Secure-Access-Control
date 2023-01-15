package com.aueb.springloginsecurity;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.io.IOException;

import static com.aueb.springloginsecurity.MyUserDetailsService.MAX_FAILED_ATTEMPTS;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    Model model;
    private int remainingAttempts;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        Users user = myUserDetailsService.loadUsersByUsername(username);

        if (user != null) {
            if (user.getLogin_attempts() < MAX_FAILED_ATTEMPTS) {
                myUserDetailsService.increaseLoginAttempts(user);
            } else {
                myUserDetailsService.lock(user);
                exception = new LockedException("Your account has been locked due to 3 failed attempts");
            }
        }
        remainingAttempts = myUserDetailsService.findRemainingAttemptsByUsername(user.getUsername());
        //model.addAttribute("remainingAttempts", remainingAttempts);

        request.setAttribute("remainingAttempts", remainingAttempts);
        super.setDefaultFailureUrl("/login?error=true");
        super.onAuthenticationFailure(request, response, exception);
    }
}
