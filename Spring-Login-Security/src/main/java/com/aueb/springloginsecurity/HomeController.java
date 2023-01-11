package com.aueb.springloginsecurity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/home")
    public String home() {
        return "HOME";
    }
    @GetMapping("/admin")
    public String admin() {
        return "ADMIN";
    }
}
