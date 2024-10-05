package org.example.notecollectorv2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("api/v1/welcome")
public class WelcomeController {
    @GetMapping
    public String welcome(Model model) {
        model.addAttribute("message", "Welcome to the Note Collector");
        return "Welcome";
    }
}
