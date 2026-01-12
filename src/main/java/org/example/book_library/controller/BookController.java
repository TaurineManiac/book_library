package org.example.book_library.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.book_library.domain.Book;
import org.example.book_library.dto.request.BookCreateRequest;
import org.example.book_library.dto.response.BookResponse;
import org.example.book_library.service.inter.BookServiceInter;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<Book>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size // Ставим просто число
    ) {
        return ResponseEntity.ok(bookService.getBooks(page, size));
    }

    @PutMapping("/update")
    public ResponseEntity<BookResponse> updateBook(@RequestParam Long id,@Valid @RequestBody BookCreateRequest bookCreateRequest) {
        return ResponseEntity.ok(bookService.updateBook(id,bookCreateRequest));
    }

    @DeleteMapping("/delete")
    public void deleteBook(@RequestParam Long id) {
        bookService.deleteBookById(id);
    }

    @GetMapping("/find")
    public ResponseEntity<Book> findBook(@RequestParam Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }
}
