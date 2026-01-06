package org.example.book_library.mapper.inter;

import org.example.book_library.domain.Book;
import org.example.book_library.dto.request.BookCreateRequest;
import org.example.book_library.dto.response.BookResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapperInter {

    Book bookRequestToEntity(BookCreateRequest bookCreateRequest);

    @Mapping(target = "success", constant = "true" )
    @Mapping(target = "message", constant = "Book created successfully")
    BookResponse entityToBookResponse(Book book);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(BookCreateRequest bookCreateRequest, @MappingTarget Book book);
}
