package com.leoncarraro.redditclone.service;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leoncarraro.redditclone.dto.RegisterRequest;
import com.leoncarraro.redditclone.model.NotificationEmail;
import com.leoncarraro.redditclone.model.User;
import com.leoncarraro.redditclone.model.VerificationToken;
import com.leoncarraro.redditclone.repository.UserRepository;
import com.leoncarraro.redditclone.repository.VerificationTokenRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

	private final PasswordEncoder passwordEncoder;
	private final MailService mailService;
	private final UserRepository userRepository;
	private final VerificationTokenRepository verificationTokenRepository;
	
	@Transactional
	public void signup(RegisterRequest registerRequest) {
		User user = new User(registerRequest, passwordEncoder);
		userRepository.save(user);
		
		String token = generateVerificationToken(user);
		mailService.sendMail(new NotificationEmail(
				"Please, activate your Reddit clone account!",
				user.getEmail(),
				"Thank you for signing up to Reddit clone, please click on the below URL to activate your account: " +
					"http://localhost:8080/api/auth/accountVerification/" + token));
	}
	
	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken(user, token);
		verificationTokenRepository.save(verificationToken);
		
		return token;
	}
	
}
