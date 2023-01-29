package com.SpringBootSecureAccessControl.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;


import java.io.IOException;

import static com.SpringBootSecureAccessControl.config.MyUserDetailsService.MAX_FAILED_ATTEMPTS;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private int remainingAttempts;
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        Users user = myUserDetailsService.loadUsersByUsername(username);

        if (user != null) {
            if (user.getLogin_attempts() == MAX_FAILED_ATTEMPTS) {
                myUserDetailsService.lock(user);
                exception = new LockedException("Your account has been locked due to 4 failed attempts");
            } else if (user.getLogin_attempts() < MAX_FAILED_ATTEMPTS) {
                myUserDetailsService.increaseLoginAttempts(user);
            }
        }

        remainingAttempts = myUserDetailsService.findRemainingAttemptsByUsername(username);

        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        session.setAttribute("remainingAttempts", remainingAttempts);

        super.setDefaultFailureUrl("/login");
        super.onAuthenticationFailure(request, response, exception);
    }
}
