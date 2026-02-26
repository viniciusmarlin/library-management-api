package br.com.viniciusmarlin.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "books")
public class BookModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String title;
    private String author;
    private boolean isAvailable;


    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "book")
    private List<LoanModel> loans;
}
