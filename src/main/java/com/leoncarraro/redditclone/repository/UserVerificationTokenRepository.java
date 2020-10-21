package com.leoncarraro.redditclone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leoncarraro.redditclone.model.UserVerificationToken;

@Repository
public interface UserVerificationTokenRepository extends JpaRepository<UserVerificationToken, Long> {
	
	Optional<UserVerificationToken> findByToken(String token);
	
}
