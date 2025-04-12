package com.nir.shopping.tracker.controller;

import com.nir.shopping.tracker.domain.RefreshToken;
import com.nir.shopping.tracker.domain.User;
import com.nir.shopping.tracker.dto.AuthRequest;
import com.nir.shopping.tracker.dto.AuthResponse;
import com.nir.shopping.tracker.repository.RefreshTokenRepository;
import com.nir.shopping.tracker.repository.UserRepository;
import com.nir.shopping.tracker.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.SplittableRandom;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;


    public AuthController(UserRepository userRepository, JwtUtil jwtUtil, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        Optional<User> userOpt = userRepository.findByUserName(request.getUserName());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        User user = userOpt.get();
        if (!BCrypt.checkpw(request.getPassword(), user.getHashPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user);

        // Invalidate old tokens and generate a new one
        refreshTokenRepository.deleteByUser(user);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(7));
        refreshTokenRepository.save(refreshToken);

        return ResponseEntity.ok(new AuthResponse(token, refreshToken.getToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestHeader("Authorization") String tokenHeader) {
        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Missing or invalid Authorization header");
        }

        String refreshTokenValue = tokenHeader.substring(7);
        Optional<RefreshToken> tokenOpt = refreshTokenRepository.findByToken(refreshTokenValue);

        if (tokenOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }

        RefreshToken refreshToken = tokenOpt.get();
        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token expired");
        }

        String newAccessToken = jwtUtil.generateToken(refreshToken.getUser());
        return ResponseEntity.ok(Collections.singletonMap("accessToken", newAccessToken));
    }

    @GetMapping("/hash-generator/{password}/password")
    public void hasPasswordGenerator(@PathVariable("password") String password){
        String salt = BCrypt.gensalt(12);
        String hashed = BCrypt.hashpw(password, salt);
        log.info("Hashed password: {}\nSalt: {}", hashed, salt);
    }
}

