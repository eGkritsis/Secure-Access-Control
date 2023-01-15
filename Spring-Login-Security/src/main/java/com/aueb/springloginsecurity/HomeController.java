package com.aueb.springloginsecurity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
