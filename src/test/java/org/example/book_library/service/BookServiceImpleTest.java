package org.example.book_library.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.book_library.domain.Book;
import org.example.book_library.dto.request.BookCreateRequest;
import org.example.book_library.dto.response.BookResponse;
import org.example.book_library.mapper.inter.BookMapperInter;
import org.example.book_library.repository.inter.BookRepositoryInter;
import org.example.book_library.service.imple.BookServiceImple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImpleTest {

    @Mock
    BookRepositoryInter bookRepositoryInter;

    @Mock
    BookMapperInter bookMapperInter;

    @InjectMocks
    BookServiceImple bookServiceImple;

    @Test
    @DisplayName("Should display book per id")
    void shouldFindBookPerId() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("title");

        when(bookRepositoryInter.findById(1L)).thenReturn(Optional.of(book));

        Book result = bookServiceImple.getBookById(1L);

        assertNotNull(result);
        assertEquals(book.getTitle(), result.getTitle());

        verify(bookRepositoryInter,times(1)).findById(1L);

    }

    @Test
    @DisplayName("Should display NotFoundEntityException exception")
    void shouldDisplayNotFoundEntityException() {
        Book book = new Book();
        book.setId(1L);

        when(bookRepositoryInter.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,() ->{
           bookServiceImple.getBookById(1L);
        });


        verify(bookRepositoryInter,times(1)).findById(1L);
        verifyNoInteractions(bookMapperInter);
    }

    @Test
    @DisplayName("Should display Book entity")
    void shouldDisplayBookResponseEntityAfterCreatingBook() {
        Book book = new Book();
        BookCreateRequest bookCreateRequest = new BookCreateRequest();
        BookResponse bookResponse = new BookResponse();

        bookResponse.setId(1L);
        bookResponse.setTitle("title");
        book.setId(1L);
        book.setTitle("title");
        bookCreateRequest.setTitle("title");

        when(bookMapperInter.bookRequestToEntity(bookCreateRequest)).thenReturn(book);
        when(bookMapperInter.entityToBookResponse(book)).thenReturn(bookResponse);
        when(bookRepositoryInter.save(book)).thenReturn(book);

        BookResponse result = bookServiceImple.addBook(bookCreateRequest);

        assertNotNull(book);
        assertEquals(bookCreateRequest.getTitle(), result.getTitle());
        verify(bookMapperInter,times(1)).bookRequestToEntity(bookCreateRequest);
        verify(bookMapperInter,times(1)).entityToBookResponse(book);
        verify(bookRepositoryInter,times(1)).save(book);

    }
}
