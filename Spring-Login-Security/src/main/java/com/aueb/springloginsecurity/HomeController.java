package com.aueb.springloginsecurity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    // Paizei kai na mhn xreiazetai giati etsi kai alliws h arxikh selida tha einai panta h login.html kai meta se kanei redirect kateftheian sthn homepage.html
    //@GetMapping("/")
    //public String home() {
       // return "home";
    //}
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
