package br.com.viniciusmarlin.library.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping("/create")
    public String createUser() {
        return "Create user";
    }

    @GetMapping("/login")
    public String loginUser() {
        return "Login user";
    }
}
