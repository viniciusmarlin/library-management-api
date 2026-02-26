package br.com.viniciusmarlin.library.dto;

public class UserDTO {
    public record RegisterUserDTO(
            String name,
            String email,
            String password
    ) {}
}
