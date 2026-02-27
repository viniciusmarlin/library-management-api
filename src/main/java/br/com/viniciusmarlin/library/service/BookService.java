package br.com.viniciusmarlin.library.service;

import br.com.viniciusmarlin.library.dto.BookDTO;
import br.com.viniciusmarlin.library.model.BookModel;
import br.com.viniciusmarlin.library.repository.IBookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/*
 * A camada de serviço é responsável por conter a lógica de negócio da aplicação.
 * Ela atua como uma ponte entre os controladores (que lidam com as requisições HTTP)
 * e os repositórios (que lidam com a persistência de dados).
 *
 * O serviço de livros (BookService) é responsável por gerenciar as operações relacionadas aos livros,
 * como criar um novo livro, listar livros, buscar um livro por ID, deletar um livro, etc.
 */

@Service
public class BookService {

    private final IBookRepository bookRepository; // Injeção de dependência do repositório de livros

    // Construtor para injetar o repositório de livros
    public BookService(IBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    //

    public BookModel create(BookDTO.CreateBookDTO dto) {

        // Criar um novo livro com os dados do DTO
        BookModel book = new BookModel();
        book.setTitle(dto.title());
        book.setAuthor(dto.author());
        book.setAvailable(true);

        return bookRepository.save(book); // Salvar o livro no banco de dados
    }

    // Listar todos os livros
    public List<BookDTO.CreateBookDTO> listAll() {

        // Buscar todos os livros no banco de dados e mapear para DTOs
        return bookRepository.findAll()
                .stream()
                .map(book -> new BookDTO.CreateBookDTO(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor()
                ))
                .toList();
    }

    // Listar apenas os livros disponíveis
    public List<BookDTO.CreateBookDTO> searchAvailableBooks() {

        // Buscar apenas os livros disponíveis no banco de dados e mapear para DTOs
        return bookRepository.findByIsAvailableTrue()
                .stream()
                .filter(BookModel::isAvailable) // só os disponíveis
                .map(book -> new BookDTO.CreateBookDTO(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor()
                ))
                .toList();
    }

    // Buscar um livro por ID
    public BookModel searchById(UUID id) {
        // Buscar um livro pelo ID no banco de dados, lançando uma exceção se não for encontrado
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    // Deletar um livro por ID
    public void deleteById(UUID id) {
        BookModel book = searchById(id);
        bookRepository.delete(book);
    }
}
