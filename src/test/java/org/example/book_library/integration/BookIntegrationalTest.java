package org.example.book_library.integration;


import org.example.book_library.domain.Book;
import org.example.book_library.dto.request.BookCreateRequest;
import org.example.book_library.repository.inter.BookRepositoryInter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Testcontainers
@ActiveProfiles("test")
public class BookIntegrationalTest{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepositoryInter bookRepositoryInter;

    @Autowired
    private ObjectMapper objectMapper;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16-alpine");

    @Test
    @DisplayName("Should put book into a table")
    void bookAdd_BookRequestInContent_BookMustBeAddedToDatabase() throws Exception {
        BookCreateRequest bookCreateRequest = new BookCreateRequest();
        bookCreateRequest.setTitle("Book Title");
        bookCreateRequest.setAuthor("Author");
        bookCreateRequest.setReleaseDate(LocalDate.of(1970,1,1));

        mockMvc.perform(post("/api/library/books/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookCreateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Book Title"))
                .andExpect(jsonPath("$.author").value("Author"))
                .andExpect(jsonPath("$.releaseDate").value(LocalDate.of(1970,1,1).toString()));

        List<Book> list= bookRepositoryInter.findAll();

        assertThat(list).hasSize(1);

        Long id =  list.getFirst().getId();

        mockMvc.perform(get("/api/library/books/find")
                .param("id", id.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Book Title"))
                .andExpect(jsonPath("$.author").value("Author"))
                .andExpect(jsonPath("$.releaseDate").value(LocalDate.of(1970,1,1).toString()));
    }
}



//@SpringBootTest
//@AutoConfigureMockMvc
//@Transactional
//@Testcontainers
//@ActiveProfiles("test")
//public class BookIntegrationalTest {
//
//    @Container
//    @ServiceConnection
//    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private BookRepositoryInter bookRepositoryInter;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    @DisplayName("Should put book into a table")
//    void shouldPutBookIntoTable() throws Exception {
//        BookCreateRequest request = new BookCreateRequest();
//        request.setTitle("Book Title");
//        request.setAuthor("Book Author");
//        request.setReleaseDate(LocalDate.of(1970, 1, 1));
//
//        mockMvc.perform(post("/api/library/books/add")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk());
//
//        List<Book> books = bookRepositoryInter.findAll();
//        assertThat(books).hasSize(1);
//
//        Long id = books.get(0).getId();
//
//        mockMvc.perform(get("/api/library/books/find")
//                        .param("id", id.toString()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.title").value("Book Title"));
//    }
//}