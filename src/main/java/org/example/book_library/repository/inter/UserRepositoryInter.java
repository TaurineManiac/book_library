package org.example.book_library.repository.inter;

import org.example.book_library.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositoryInter extends JpaRepository<User,Long> {

    public Optional findByUsername(String username);

}
