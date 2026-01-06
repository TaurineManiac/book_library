//package org.example.book_library.config;
//
//import lombok.RequiredArgsConstructor;
//import org.example.book_library.domain.Book;
//import org.example.book_library.dto.request.BookCreateRequest;
//import org.example.book_library.repository.inter.BookRepositoryInter;
//import org.example.book_library.service.inter.BookServiceInter;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//
//@Component
//@RequiredArgsConstructor
//@Profile({"dev","test"})
//public class DataInitializer implements CommandLineRunner {
//    final BookServiceInter bookServiceInter;
//
//    @Override
//    public void run(String... args) throws Exception {
//        BookCreateRequest book1 = BookCreateRequest.builder()
//                .title("Я такой как все")
//                .author("Олег Тинькофф")
//                .releaseDate(LocalDate.of(2010,1,1))
//                .build();
//        bookServiceInter.update(book1);
//        Book book2 = Book.builder()
//                .title("Мастер и Маргарита")
//                .author("Михаил Булгаков")
//                .releaseDate(LocalDate.of(1967,1,1))
//                .build();
//        bookServiceInter.addBook(book2);
//        Book book3 = Book.builder()
//                .title("Евгений Онегин")
//                .author("Александр Пушкин")
//                .releaseDate(LocalDate.of(2010,1,1))
//                .build();
//        bookServiceInter.addBook(book3);
//    }
//
//    private final BookRepositoryInter bookRepository;
//
//}
