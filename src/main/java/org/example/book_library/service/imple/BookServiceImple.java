package org.example.book_library.service.imple;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.example.book_library.domain.Book;
import org.example.book_library.dto.request.BookCreateRequest;
import org.example.book_library.dto.response.BookResponse;
import org.example.book_library.mapper.inter.BookMapperInter;
import org.example.book_library.repository.inter.BookRepositoryInter;
import org.example.book_library.service.inter.BookServiceInter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<Book> getBooks(int page,int size) {
        Pageable pageable = PageRequest.of(page,size);
        return bookRepositoryInter.findAll(pageable);
    }

    @Transactional
    @Override
    public BookResponse updateBook(Long id,BookCreateRequest bookCreateRequest) {
        Book existingBook=bookRepositoryInter.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Книга с id " + id + " не найдена."));
        bookMapperInter.updateEntityFromDto(bookCreateRequest, existingBook);
        bookRepositoryInter.save(existingBook);
        return bookMapperInter.entityToBookResponse(existingBook);
    }

    @Transactional
    @Override
    public void deleteBookById(Long id) {
        bookRepositoryInter.findById(id).orElseThrow(()-> new EntityNotFoundException("Книга с id " + id + " не найдена."));
        bookRepositoryInter.deleteById(id);
    }

    public Book getBookById(Long id) {
        return bookRepositoryInter.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Книга с id " + id + " не найдена."));
    }
}
