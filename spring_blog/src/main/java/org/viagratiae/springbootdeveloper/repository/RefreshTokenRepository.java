package org.viagratiae.springbootdeveloper.repository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.viagratiae.springbootdeveloper.domain.RefreshToken;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUserId(Long UserId);  // QueryCreation을 통해 메소드 자동 구현
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
