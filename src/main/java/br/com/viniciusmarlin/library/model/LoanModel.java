package br.com.viniciusmarlin.library.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class LoanModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookModel book;

    private LocalDateTime loanDate;

    private LocalDateTime returnDate;

}
