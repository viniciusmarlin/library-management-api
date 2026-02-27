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
    @PostMapping("/login")
    public ResponseEntity<UserDTO.UserResponseDTO> login(
            @RequestBody UserDTO.LoginUserDTO dto) {

        var userLogged = userService.login(
                dto.email(),
                dto.password()
        );

        var response = new UserDTO.UserResponseDTO(
                userLogged.getId(),
                userLogged.getName(),
                userLogged.getEmail()
        );
        return ResponseEntity.ok(response);
    }
}
