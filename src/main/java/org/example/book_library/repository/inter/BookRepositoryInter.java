package org.example.book_library.repository.inter;

import org.example.book_library.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepositoryInter extends JpaRepository<Book,Long> {

}
