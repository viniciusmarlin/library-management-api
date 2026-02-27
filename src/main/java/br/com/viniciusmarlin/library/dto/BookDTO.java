package br.com.viniciusmarlin.library.dto;

import java.util.UUID;

/*
 * DTO (Data Transfer Object)
 * Tem utilidade para controlar a entrada e saída de dados,
 * especialmente em operações como registro e login de usuários.
 */

public class BookDTO {

    // DTO para criação de livro
    public record CreateBookDTO(
            UUID id,
            String title,
            String author
    ) {}

    // DTO para resposta de livro (sem informações sensíveis)
    public record ResponseBookDTO(
            String title,
            String author
    ) {}
}

