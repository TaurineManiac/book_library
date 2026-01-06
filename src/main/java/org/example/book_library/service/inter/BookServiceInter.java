package org.example.book_library.service.inter;

import org.example.book_library.domain.Book;
import org.example.book_library.dto.request.BookCreateRequest;
import org.example.book_library.dto.response.BookResponse;

import java.util.List;


public interface BookServiceInter{
    BookResponse addBook(BookCreateRequest bookCreateRequest);
    List<Book> getBooks();
    void updateBook(Long id,BookCreateRequest  bookCreateRequest);
    void deleteBookById(Long id);
    Book getBookById(Long id);
}
