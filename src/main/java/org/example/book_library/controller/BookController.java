package org.example.book_library.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.book_library.domain.Book;
import org.example.book_library.dto.request.BookCreateRequest;
import org.example.book_library.dto.response.BookResponse;
import org.example.book_library.service.inter.BookServiceInter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library/books")
@RequiredArgsConstructor
public class BookController {

    private final BookServiceInter bookService;

    @PostMapping("/add")
    public ResponseEntity<BookResponse> addBook(@Valid @RequestBody BookCreateRequest bookCreateRequest) {
        return ResponseEntity.ok(bookService.addBook(bookCreateRequest));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getBooks());
    }

    @PutMapping("/update")
    public ResponseEntity<List<Book>> updateBook(@RequestParam Long id,@Valid @RequestBody BookCreateRequest bookCreateRequest) {
        bookService.updateBook(id,bookCreateRequest);
        return ResponseEntity.ok(bookService.getBooks());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<List<Book>> deleteBook(@RequestParam Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.ok(bookService.getBooks());
    }

    @GetMapping("/find")
    public ResponseEntity<Book> findBook(@RequestParam Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }
}
