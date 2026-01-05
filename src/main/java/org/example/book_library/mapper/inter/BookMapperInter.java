package org.example.book_library.mapper;

import org.example.book_library.domain.Book;
import org.example.book_library.dto.request.BookCreateRequest;
import org.example.book_library.dto.response.BookResponse;
import org.springframework.stereotype.Component;

@Mapper
public interface BookMapper {
    public Book bookRequestToEntity(BookCreateRequest bookCreateRequest) {
        Book book=new Book();
        book.setAuthor(bookCreateRequest.getAuthor());
        book.setTitle(bookCreateRequest.getTitle());
        book.setReleaseDate(bookCreateRequest.getReleaseDate());
        return book;
    }

    public BookResponse entityToBookResponse(Book book) {
        if(book==null){
            return null;
        }
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .releaseDate(book.getReleaseDate())
                .message("Book created successfully")
                .success(true)
                .build();
    }
}
