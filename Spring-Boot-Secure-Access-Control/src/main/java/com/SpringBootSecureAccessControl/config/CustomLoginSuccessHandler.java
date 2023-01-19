package com.SpringBootSecureAccessControl.config;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        Users user = myUserDetails.getUser();

        LocalDateTime lastPasswordChange = user.getLastPasswordChange();
        LocalDateTime now = LocalDateTime.now();
        long remainingMinutes = 90*24*60 - ChronoUnit.MINUTES.between(lastPasswordChange, now);



        HttpSession session = request.getSession();
        session.setAttribute("remainingMinutes", remainingMinutes);
        session.setAttribute("lastPasswordChange", lastPasswordChange);

        if (user.getLogin_attempts() > 0) {
            myUserDetailsService.resetLoginAttempts(user.getUsername());
        }
        response.sendRedirect("homepage.html");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
