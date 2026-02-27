package br.com.viniciusmarlin.library.dto;

/*
 * DTO (Data Transfer Object)
 * Tem utilidade para controlar a entrada e saída de dados,
 * especialmente em operações como registro e login de usuários.
 */

public class LoanDTO {
    // DTO para criar um empréstimo
    public record CreateLoanDTO(
            String userId,
            String bookId
    ) {
    }

    // DTO para resposta de empréstimo
     public record LoanResponseDTO(
             String id,
             String userId,
             String bookId,
             String loanDate,
             String returnDate
     ) {
     }
}
