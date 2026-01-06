package org.example.book_library.service.imple;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.example.book_library.domain.Book;
import org.example.book_library.dto.request.BookCreateRequest;
import org.example.book_library.dto.response.BookResponse;
import org.example.book_library.mapper.inter.BookMapperInter;
import org.example.book_library.repository.inter.BookRepositoryInter;
import org.example.book_library.service.inter.BookServiceInter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImple implements BookServiceInter {

    private final BookRepositoryInter bookRepositoryInter;
    private final BookMapperInter bookMapperInter;

    @Transactional
    @Override
    public BookResponse addBook(BookCreateRequest bookCreateRequest) {
        Book existingBook= bookMapperInter.bookRequestToEntity(bookCreateRequest);
        return bookMapperInter.entityToBookResponse(bookRepositoryInter.save(existingBook));
    }

    @Override
    public List<Book> getBooks() {
        return bookRepositoryInter.findAll();
    }

    @Transactional
    @Override
    public void updateBook(Long id,BookCreateRequest bookCreateRequest) {
        Book existingBook=bookRepositoryInter.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Книга с id " + id + " не найдена."));
        bookMapperInter.updateEntityFromDto(bookCreateRequest, existingBook);
        bookRepositoryInter.save(existingBook);
    }

    @Transactional
    @Override
    public void deleteBookById(Long id) {
        bookRepositoryInter.findById(id).orElseThrow(()-> new EntityNotFoundException("Книга с id " + id + " не найдена."));
        bookRepositoryInter.deleteById(id);
    }

    @Override
    public Book getBookById(Long id) {
        bookRepositoryInter.findById(id).orElseThrow(()-> new EntityNotFoundException("Книга с id " + id + " не найдена."));
        return bookRepositoryInter.findById(id).orElseThrow(() -> new RuntimeException("Book Not Found with "+ id + " id"));
    }
}
