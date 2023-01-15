package com.aueb.springloginsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    //private JdbcTemplate jdbcTemplate;
    public static final int MAX_FAILED_ATTEMPTS = 3;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoggingService loggingService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        loggingService.logSuccessfulLogin(username);
        return new MyUserDetails(user);
    }

    public Users loadUsersByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        loggingService.logSuccessfulLogin(username);
        return user;
    }


    // This method updates the number of failed attempts of a user. It is called each time the user fails to login (e.g. providing wrong username or password).
    public void increaseLoginAttempts(Users user) {
        int newLoginAttempts = user.getLogin_attempts() + 1;
        userRepository.updateLoginAttempts(newLoginAttempts, user.getUsername());
       // String sql = "UPDATE gdpr.users SET login_attempts = ? WHERE username = ?";
       // jdbcTemplate.update(sql, newLoginAttempts, user.getUsername());
    }

    // Sets the number of failed attempts to zero. This method will be called when the user has logged in successfully.
    public void resetLoginAttempts(String username) {
        userRepository.updateLoginAttempts(0, username);
       // String sql = "UPDATE gdpr.users SET login_attempts = 0 WHERE username = ?";
        //jdbcTemplate.update(sql, username);
    }

    // Locks the user’s account if the number of failed logins reach the maximum allowed times.
    public void lock(Users user) {
        user.setAccountNonLocked(false);
        userRepository.save(user);
       // String sql = "UPDATE gdpr.users SET account_non_locked = false WHERE username = ?";
        //jdbcTemplate.update(sql, user.getUsername());
    }

    public int findRemainingAttemptsByUsername(String username) {
        Users user = userRepository.findByUsername(username);
        int remainingAttempts = (MAX_FAILED_ATTEMPTS - user.getLogin_attempts());

        return remainingAttempts;
    }
}
