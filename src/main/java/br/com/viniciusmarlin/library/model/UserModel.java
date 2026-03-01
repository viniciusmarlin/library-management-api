package br.com.viniciusmarlin.library.model;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.CreationTimestamp;

//import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "users")
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String name;
    private String email;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<LoanModel> loans = new ArrayList<>();
}
