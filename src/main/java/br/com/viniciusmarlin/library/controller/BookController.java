package br.com.viniciusmarlin.library.controller;

import br.com.viniciusmarlin.library.dto.BookDTO;
import br.com.viniciusmarlin.library.model.BookModel;
import br.com.viniciusmarlin.library.service.BookService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/books")
public class BookController {

    // Injeção de dependência do serviço de livros
    public final BookService bookService;

    // Construtor para injetar o serviço de livros
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Admin
    @PostMapping("/create")
    public ResponseEntity<BookModel> createBook(@RequestBody BookDTO.CreateBookDTO dto) {
        var book = bookService.create(dto);
        return ResponseEntity.ok(book);
    }

    // User
    // Listar todos os livros
    @GetMapping("/all")
    public ResponseEntity<List<BookDTO.ResponseBookDTO>> listBooks() {
        List<BookDTO.CreateBookDTO> createBookDTOs = bookService.listAll();

        List<BookDTO.ResponseBookDTO> bookDTOs = createBookDTOs.stream()
                .map(create -> new BookDTO.ResponseBookDTO(
                        create.title(),
                        create.author()
                ))
                .toList();

        return ResponseEntity.ok(bookDTOs);
    }

    // User
    // Listar apenas os livros disponíveis
    @GetMapping("/available")
    public ResponseEntity<List<BookDTO.ResponseBookDTO>> listAllBooksAvailable() {
        List<BookDTO.CreateBookDTO> createBookDTOs = bookService.searchAvailableBooks();

        List<BookDTO.ResponseBookDTO> bookDTOs = createBookDTOs.stream()
                .map(create -> new BookDTO.ResponseBookDTO(
                        create.title(),
                        create.author()
                ))
                .toList();

        return ResponseEntity.ok(bookDTOs);
    }

    //User
    // Buscar um livro por ID
    @GetMapping("/{id}")
    public ResponseEntity<BookModel> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(bookService.searchById(id));
    }

    // Admin
    // Deletar um livro por ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable UUID id) {
        try {
            bookService.deleteById(id);
            return ResponseEntity.ok("Livro deletado com sucesso!");
        } catch (NoSuchElementException | EmptyResultDataAccessException e) {
            // se o livro não existe
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Livro não encontrado!");
        } catch (Exception e) {
            // qualquer outro erro
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar o livro: " + e.getMessage());
        }
    }
}


// update book