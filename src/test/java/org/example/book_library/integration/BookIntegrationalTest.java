package org.example.book_library.bookIntegrationalTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.book_library.repository.inter.BookRepositoryInter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Testcontainers
public class BookIntegrationalTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepositoryInter bookRepositoryInter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() {
        // Пустой тест для проверки, что контекст и Docker запускаются
    }
}