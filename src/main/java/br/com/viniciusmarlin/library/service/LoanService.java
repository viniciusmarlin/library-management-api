package br.com.viniciusmarlin.library.service;

import br.com.viniciusmarlin.library.dto.LoanDTO;
import br.com.viniciusmarlin.library.exception.*;
import br.com.viniciusmarlin.library.model.BookModel;
import br.com.viniciusmarlin.library.model.LoanModel;
import br.com.viniciusmarlin.library.model.LoanStatus;
import br.com.viniciusmarlin.library.model.UserModel;
import br.com.viniciusmarlin.library.repository.IBookRepository;
import br.com.viniciusmarlin.library.repository.ILoanRepository;
import br.com.viniciusmarlin.library.repository.IUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/*
 * A camada de serviço é responsável por conter a lógica de negócio da aplicação.
 * Ela atua como uma ponte entre os controladores (que lidam com as requisições HTTP)
 * e os repositórios (que lidam com a persistência de dados).
 *
 * O serviço de empréstimos (LoanService) é responsável por gerenciar as operações relacionadas aos empréstimos de livros,
 * como criar um novo empréstimo, listar empréstimos, devolver um livro, etc.
 */

@Service
public class LoanService {
    // Injeção de dependência do ILoanRepository, IUserRepository e IBookRepository
    private final ILoanRepository loanRepository;
    private final IUserRepository userRepository;
    private final IBookRepository bookRepository;

    //  Construtor para injetar os repositórios
    public LoanService(ILoanRepository loanRepository, IUserRepository userRepository, IBookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }


    private LoanDTO.CreateLoanDTO toDTO(LoanModel loan) {
        return new LoanDTO.CreateLoanDTO(
                loan.getId(),
                loan.getUser().getId(),
                loan.getBook().getId(),
                loan.getLoanDate(),
                loan.getReturnDate()
        );
    }

    @Transactional
    public LoanDTO.CreateLoanDTO createLoan(UUID userId, UUID bookId) {
        UserModel user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        BookModel book = bookRepository.findById(bookId)
            .orElseThrow(() -> new LoanNotFoundException("Loan not found"));



        boolean bookIsLoaned = loanRepository
                .existsByBookAndStatus(book, LoanStatus.ACTIVE);

        if (bookIsLoaned) {
            throw new BusinessException("Livro já está emprestado");
        }

        boolean alreadyLoaned = loanRepository.existsByUserAndBookAndStatus(user, book, LoanStatus.ACTIVE);

        if (alreadyLoaned) {
            throw new UserAlreadyHasActiveLoanException("Usuário já possui este livro emprestado");
        }

        LoanModel loan = new LoanModel();
            loan.setUser(user);
            loan.setBook(book);
            loan.setLoanDate(LocalDateTime.now());
        loan.setDueDate(LocalDateTime.now().minusMinutes(1));
            loan.setStatus(LoanStatus.ACTIVE);


        book.setAvailable(false);
        bookRepository.save(book);
        LoanModel savedLoan = loanRepository.save(loan);
        return toDTO(savedLoan);
    }

    public LoanModel findLoan(UUID loanId) {
        // Buscar um livro pelo ID no banco de dados, lançando uma exceção se não for encontrado
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));
    }

    @Transactional
    public void returnLoan(UUID loanId, UUID bookId) {

        BookModel book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Livro não encontrado"));

        LoanModel loan = findLoan(loanId);

        if (!loan.isActive() && !loan.isLate()) {
            throw new BusinessException("Loan already closed");
        }

        loan.setReturnDate(LocalDateTime.now());
        loan.setStatus(LoanStatus.RETURNED);

        book.setAvailable(true);

        loanRepository.save(loan);
        bookRepository.save(book);
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void updateLateLoans() {

        System.out.println("Scheduler rodando...");

        List<LoanModel> lateLoans =
                loanRepository.findByStatusAndDueDateBefore(
                        LoanStatus.ACTIVE,
                        LocalDateTime.now()
                );

        for (LoanModel loan : lateLoans) {
            loan.setStatus(LoanStatus.LATE);
        }

        loanRepository.saveAll(lateLoans);
    }

    @Transactional
    public List<LoanDTO.CreateLoanDTO> findLoansByUser(UUID userId) {
        return loanRepository.findByUserId(userId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional
    public List<LoanDTO.CreateLoanDTO> findActiveLoansByUser(UUID userId) {
        return loanRepository.findByUserIdAndStatus(userId, LoanStatus.ACTIVE)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional
    public List<LoanDTO.CreateLoanDTO> findLateLoansByUser(UUID userId) {
        return loanRepository.findByUserIdAndStatus(userId, LoanStatus.LATE)
                .stream()
                .map(this::toDTO)
                .toList();
    }
}