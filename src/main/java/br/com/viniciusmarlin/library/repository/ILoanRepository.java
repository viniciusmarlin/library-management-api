package br.com.viniciusmarlin.library.repository;

import br.com.viniciusmarlin.library.model.BookModel;
import br.com.viniciusmarlin.library.model.LoanModel;
import br.com.viniciusmarlin.library.model.LoanStatus;
import br.com.viniciusmarlin.library.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ILoanRepository extends JpaRepository<LoanModel, UUID> {
    // Método para encontrar empréstimos por usuário
    List<LoanModel> findByUserId(
            UUID userId
    );

    List<LoanModel> findByUserIdAndStatus(UUID userId, LoanStatus status);

    boolean existsByUserAndBookAndStatus(
            UserModel user,
            BookModel book,
            LoanStatus status
    );

    boolean existsByBookAndStatus(
            BookModel book, LoanStatus status
    );

}
