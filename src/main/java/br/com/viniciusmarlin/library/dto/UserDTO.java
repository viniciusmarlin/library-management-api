package br.com.viniciusmarlin.library.dto;


import java.util.UUID;

/*
* DTO (Data Transfer Object)
* Tem utilidade para controlar a entrada e saída de dados,
* especialmente em operações como registro e login de usuários.
 */
public class UserDTO {

    // DTO para registro de usuário
    public record RegisterUserDTO(
            String name,
            String email,
            String password
    ) {}

    // DTO para login de usuário
    public record LoginUserDTO(
            UUID id,
            String email,
            String password
    ) {}
    // DTO para resposta de usuário (sem senha)
    public record UserResponseDTO(
            UUID id,
            String name,
            String email
    ) {}
}
