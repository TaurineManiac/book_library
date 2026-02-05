package org.example.book_library.repository.inter;

import org.example.book_library.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryInter extends JpaRepository<User,Long> {

}
