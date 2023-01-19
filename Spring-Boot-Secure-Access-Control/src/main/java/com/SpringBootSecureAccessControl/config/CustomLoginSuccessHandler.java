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
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
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

        long totalMinutes = 90*24*60;
        LocalDateTime expirationDate = lastPasswordChange.plusMinutes(totalMinutes);
        Duration remainingDuration = Duration.between(now, expirationDate);

        Period remainingPeriod = Period.between(now.toLocalDate(), expirationDate.toLocalDate());
        long remainingSeconds = remainingDuration.getSeconds();
        long remainingMinutes = remainingDuration.toMinutes();
        long remainingHours = remainingDuration.toHours();
        long remainingDays = remainingDuration.toDays();
        long remainingMonths = remainingPeriod.getMonths();
        long remainingYears = remainingPeriod.getYears();


        String formattedRemainingTime = remainingYears + " years, " + remainingMonths + " months, " + remainingDays % 30 + " days, " + remainingHours % 24 + " hours, " + remainingMinutes % 60 + " minutes, " + remainingSeconds % 60 + " seconds.";


        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        session.setAttribute("formattedRemainingTime", formattedRemainingTime);
        session.setAttribute("lastPasswordChange", lastPasswordChange);

        if (user.getLogin_attempts() > 0) {
            myUserDetailsService.resetLoginAttempts(user.getUsername());
        }
        response.sendRedirect("homepage.html");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
