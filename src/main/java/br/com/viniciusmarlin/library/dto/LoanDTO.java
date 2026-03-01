package br.com.viniciusmarlin.library.dto;

/*
 * DTO (Data Transfer Object)
 * Tem utilidade para controlar a entrada e saída de dados,
 * especialmente em operações como registro e login de usuários.
 */

import java.time.LocalDateTime;
import java.util.UUID;

public class LoanDTO {
    // DTO para criar um empréstimo
    public record CreateLoanDTO(
            String userId,
            String bookId
    ) {
    }

    // DTO para resposta de empréstimo
     public record LoanResponseDTO(
             UUID id,
             UUID userId,
             UUID bookId,
             LocalDateTime loanDate,
             LocalDateTime returnDate
     ) {
     }
}
