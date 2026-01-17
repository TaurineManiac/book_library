package org.example.book_library.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.book_library.domain.Book;
import org.example.book_library.dto.request.BookCreateRequest;
import org.example.book_library.dto.response.BookResponse;
import org.example.book_library.service.inter.BookServiceInter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    final private MockMvc mockMvc;

    final private ObjectMapper objectMapper;

    @MockitoBean
    private BookServiceInter bookService;

    @Autowired
    public BookControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    @DisplayName("GET /api/library/books/find - Should display book detail if ID is valid or exception")
    void findBook_IdInParams_ReturnBook() throws Exception {
        Book book = new Book();
        Long id = 1L;
        book.setId(id);
        book.setTitle("title");
        book.setAuthor("author");

        when(bookService.getBookById(1L)).thenReturn(book);

        mockMvc.perform(get("/api/library/books/find").param("id", id.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.author").value("author"));

        verify(bookService,times(1)).getBookById(id);
    }

    @Test
    @DisplayName("GET /api/library/books/find - Should return 404 if book not found")
    void findBook_IdInParams_Returns404() throws Exception {
        Long id = 1L;
        when (bookService.getBookById(id)).thenThrow(new EntityNotFoundException("Book not found"));

        mockMvc.perform(get("/api/library/books/find")
                .param("id", id.toString()))
                .andExpect(status().isNotFound());

        verify(bookService,times(1)).getBookById(id);
    }

    @Test
    @DisplayName("POST /api/library/books/add - Should return BookResponse")
    void addBook_BookRequest_ReturnBookResponse() throws Exception {
        Long id = 1L;

        BookCreateRequest bookRequest = new BookCreateRequest();
        bookRequest.setTitle("title");
        bookRequest.setAuthor("author");
        bookRequest.setReleaseDate(LocalDate.of(1970, 1, 1));

        BookResponse bookResponse = new BookResponse();
        bookResponse.setTitle("title");
        bookResponse.setAuthor("author");
        bookResponse.setId(id);
        bookResponse.setReleaseDate(LocalDate.parse("1970-01-01"));
        bookResponse.setMessage("Book added");
        bookResponse.setSuccess(true);

        when(bookService.addBook(any(BookCreateRequest.class))).thenReturn(bookResponse);

        mockMvc.perform(post("/api/library/books/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.author").value("author"))
                .andExpect(jsonPath("$.releaseDate").value("1970-01-01"))
                .andExpect(jsonPath("$.message").value("Book added"))
                .andExpect(jsonPath("$.success").value(true));

        verify(bookService,times(1)).addBook(any(BookCreateRequest.class));

        }

    @Test
    @DisplayName("PUT /api/library/books/update - Should return BookResponse")
    void updateBook_BookCreateRequest_ReturnBookResponse() throws Exception {
        Long id = 1L;

        BookCreateRequest bookRequest = new BookCreateRequest();
        bookRequest.setTitle("title");
        bookRequest.setAuthor("author");
        bookRequest.setReleaseDate(LocalDate.of(1970, 1, 1));

        BookResponse bookResponse = new BookResponse();
        bookResponse.setTitle("title");
        bookResponse.setAuthor("author");
        bookResponse.setId(id);
        bookResponse.setReleaseDate(LocalDate.parse("1970-01-01"));
        bookResponse.setMessage("Book updated");
        bookResponse.setSuccess(true);


        when(bookService.updateBook(eq(id), any(BookCreateRequest.class))).thenReturn(bookResponse);

        mockMvc.perform(put("/api/library/books/update")
                        .param("id", id.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.author").value("author"))
                .andExpect(jsonPath("$.releaseDate").value("1970-01-01"))
                .andExpect(jsonPath("$.message").value("Book updated"))
                .andExpect(jsonPath("$.success").value(true));


        verify(bookService,times(1)).updateBook(eq(id), any(BookCreateRequest.class));

    }

    @Test
    @DisplayName("PUT /api/library/books/update - Should return 404 if book not found")
    void updateBook_IdInParams_Returns404() throws Exception {
        BookCreateRequest bookRequest = new BookCreateRequest();
        bookRequest.setTitle("title");
        bookRequest.setAuthor("author");
        bookRequest.setReleaseDate(LocalDate.of(1970, 1, 1));
        Long id = 1L;
        when(bookService.updateBook(eq(id),any(BookCreateRequest.class))).thenThrow(new EntityNotFoundException("Book not found"));
        mockMvc.perform(put("/api/library/books/update")
                        .param("id", id.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isNotFound());
        verify(bookService,times(1)).updateBook(eq(id),any(BookCreateRequest.class));
    }

    @Test
    @DisplayName("DELETE /api/library/books/delete - Should return 200 if book was deleted")
    void deleteBook_IdInParams_Return200IfBookDeleted() throws Exception {
        Long id = 1L;

        doNothing().when(bookService).deleteBookById(eq(id));

        mockMvc.perform(delete("/api/library/books/delete")
                .param("id",id.toString()))
                .andExpect(status().isOk());
        verify(bookService,times(1)).deleteBookById(eq(id));
    }

    @Test
    @DisplayName("DELETE /api/library/books/delete - Should return 404 if book not found")
    void deleteBook_IdInParams_Return404IfBookNotFound() throws Exception {
        Long id =1L;

        doThrow(new EntityNotFoundException("Book not found")).when(bookService).deleteBookById(eq(id));

        mockMvc.perform(delete("/api/library/books/delete")
                        .param("id", id.toString()))
                .andExpect(status().isNotFound());

        verify(bookService,times(1)).deleteBookById(eq(id));
    }

    @Test
    @DisplayName("GET /api/library/books/getAll - Should return 200 and Page of info about books")
    void getAllBooks_PageAndSizeInParams_ReturnPageOfBooks() throws Exception {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1L,"title","author",LocalDate.of(1970, 1, 1)));
        bookList.add(new Book(2L,"title2","author2",LocalDate.of(1970, 1, 1)));
        bookList.add(new Book(3L,"title3","author3",LocalDate.of(1970, 1, 1)));

        Page<Book> bookPage = new PageImpl<>(bookList);

        when(bookService.getBooks(1,3)).thenReturn(bookPage);

        mockMvc.perform(get("/api/library/books/getAll")
                .param("page","1")
                .param("size","3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(3))
                .andExpect(jsonPath("$.content[0].title").value("title"))
                .andExpect(jsonPath("$.content[0].author").value("author"))
                .andExpect(jsonPath("$.content[0].releaseDate").value("1970-01-01"))
                .andExpect(jsonPath("$.content[2].title").value("title3"))
                .andExpect(jsonPath("$.content[2].author").value("author3"))
                .andExpect(jsonPath("$.content[2].releaseDate").value("1970-01-01"));

        verify(bookService,times(1)).getBooks(1,3);

    }

    @Test
    @DisplayName("GET /api/library/books/getAll - Should return 404 if book/s not found")
    void getAllBooks_PageAndSizeInParams_Return404IfBooksNotFound() throws Exception {
        Integer  page = 1;
        Integer size = 10;

        when(bookService.getBooks(eq(page),eq(size))).thenThrow(new EntityNotFoundException("Books not found"));

        mockMvc.perform(get("/api/library/books/getAll")
                        .param("page", page.toString())
                        .param("size", size.toString()))
                .andExpect(status().isNotFound());

        verify(bookService,  times(1)).getBooks(eq(page),eq(size));
    }

}
