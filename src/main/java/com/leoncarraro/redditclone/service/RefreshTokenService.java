package com.leoncarraro.redditclone.service;

import com.leoncarraro.redditclone.model.RefreshToken;
import com.leoncarraro.redditclone.repository.RefreshTokenRepository;
import com.leoncarraro.redditclone.service.exception.InvalidRefreshTokenException;
import com.leoncarraro.redditclone.service.exception.InvalidTokenException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(), Instant.now());
        refreshToken = refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }

    public void validateRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new InvalidRefreshTokenException("Invalid refresh token!"));
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

}
