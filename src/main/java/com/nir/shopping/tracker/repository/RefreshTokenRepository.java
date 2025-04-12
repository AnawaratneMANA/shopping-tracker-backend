package com.nir.shopping.tracker.repository;

import com.nir.shopping.tracker.domain.RefreshToken;
import com.nir.shopping.tracker.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
}