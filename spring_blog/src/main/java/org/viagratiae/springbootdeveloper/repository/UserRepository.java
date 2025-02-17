package org.viagratiae.springbootdeveloper.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.viagratiae.springbootdeveloper.domain.Article;
import org.viagratiae.springbootdeveloper.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);  // QueryCreation을 통해 메소드 자동 구현
}
