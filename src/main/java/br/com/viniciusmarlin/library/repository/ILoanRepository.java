package br.com.viniciusmarlin.library.repository;

import br.com.viniciusmarlin.library.model.LoanModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ILoanRepository extends JpaRepository<LoanModel, UUID> {
    // Método para encontrar empréstimos por usuário
    List<LoanModel> findByUserId(UUID userId);
    List<LoanModel> findByBookId(UUID bookId);
}
