package br.com.viniciusmarlin.library.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private List<LoanModel> loans = new ArrayList<>();
}
