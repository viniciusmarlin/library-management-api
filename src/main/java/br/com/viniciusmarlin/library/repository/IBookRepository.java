package br.com.viniciusmarlin.library.repository;

import br.com.viniciusmarlin.library.model.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IBookRepository extends JpaRepository<BookModel, UUID> {
    // Método para encontrar livros disponíveis
    List<BookModel> findByIsAvailableTrue();
}
