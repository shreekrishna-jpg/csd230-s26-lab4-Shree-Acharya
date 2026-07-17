package app.controllers;

import app.entities.BookEntity;
import app.repositories.BookRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Book Management", description = "CRUD operations for books")
@RestController
@RequestMapping("/api/rest/books")
@CrossOrigin(origins = "*")
public class BookRestController {

    private final BookRepository repository;

    public BookRestController(BookRepository repository) {
        this.repository = repository;
    }

    @Operation(summary = "Retrieve all books in the inventory")
    @ApiResponse(responseCode = "200", description = "List of books returned")
    @GetMapping
    public List<BookEntity> all() {
        return repository.findAll();
    }

    @Operation(summary = "Create a new book")
    @ApiResponse(responseCode = "200", description = "Book created successfully")
    @PostMapping
    public BookEntity newBook(@RequestBody BookEntity newBook) {
        return repository.save(newBook);
    }

    @Operation(summary = "Retrieve a single book by ID")
    @ApiResponse(responseCode = "200", description = "Book found")
    @ApiResponse(responseCode = "404", description = "Book not found")
    @GetMapping("/{id}")
    public BookEntity one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("book", id));
    }

    @Operation(summary = "Update or replace a book by ID")
    @ApiResponse(responseCode = "200", description = "Book updated")
    @PutMapping("/{id}")
    public BookEntity replaceBook(@RequestBody BookEntity newBook, @PathVariable Long id) {
        return repository.findById(id)
                .map(book -> {
                    book.setTitle(newBook.getTitle());
                    book.setAuthor(newBook.getAuthor());
                    book.setPrice(newBook.getPrice());
                    book.setCopies(newBook.getCopies());
                    return repository.save(book);
                })
                .orElseGet(() -> {
                    newBook.setId(id);
                    return repository.save(newBook);
                });
    }

    @Operation(summary = "Delete a book by ID")
    @ApiResponse(responseCode = "200", description = "Book deleted")
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        repository.deleteById(id);
    }
}