package br.com.viniciusmarlin.library.controller;

import br.com.viniciusmarlin.library.dto.UserDTO;
import br.com.viniciusmarlin.library.model.UserModel;
import br.com.viniciusmarlin.library.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    // Injeção de dependência do UserService
    public final UserService userService;

    // Construtor para injetar o UserService
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint para registrar um novo usuário
    @PostMapping("/register")
    public ResponseEntity<UserModel> register(@RequestBody UserDTO.RegisterUserDTO dto) {
        var user = userService.register(dto);
        return ResponseEntity.ok(user);
    }

    // Endpoint para login de usuário
    @GetMapping("/login")
    public ResponseEntity<UserModel> login(@RequestParam String email, @RequestParam String password) {
        var userLogged = userService.login(email, password);
        return ResponseEntity.ok(userLogged);
    }
}
