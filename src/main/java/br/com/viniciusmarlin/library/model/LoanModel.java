package br.com.viniciusmarlin.library.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "loans")
public class LoanModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private BookModel book;

    private LocalDateTime loanDate;

    private LocalDateTime returnDate;

    private LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    public boolean isActive() {
        return this.status == LoanStatus.ACTIVE;
    }

    public boolean isReturned() {
        return this.status == LoanStatus.RETURNED;
    }

    public boolean isLate() {
        return this.status == LoanStatus.ACTIVE &&
                LocalDateTime.now().isAfter(this.dueDate);
    }

}
