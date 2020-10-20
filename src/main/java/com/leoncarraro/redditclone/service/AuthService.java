package com.leoncarraro.redditclone.service;

import java.util.UUID;

import com.leoncarraro.redditclone.service.exception.UserNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leoncarraro.redditclone.dto.AuthenticationResponse;
import com.leoncarraro.redditclone.dto.LoginRequest;
import com.leoncarraro.redditclone.dto.RegisterRequest;
import com.leoncarraro.redditclone.model.NotificationEmail;
import com.leoncarraro.redditclone.model.User;
import com.leoncarraro.redditclone.model.VerificationToken;
import com.leoncarraro.redditclone.repository.UserRepository;
import com.leoncarraro.redditclone.repository.VerificationTokenRepository;
import com.leoncarraro.redditclone.security.JwtProvider;
import com.leoncarraro.redditclone.service.exception.InvalidTokenException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

	private final PasswordEncoder passwordEncoder;
	private final MailService mailService;
	private final UserRepository userRepository;
	private final VerificationTokenRepository verificationTokenRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;
	
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

	@Transactional
	public void verifyAccount(String token) {
		VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
				.orElseThrow(() -> new InvalidTokenException("Invalid token!"));
		
		fetchUserAndEnable(verificationToken);
	}
	
	public AuthenticationResponse login(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
			loginRequest.getUsername(),
			loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String authenticationToken = jwtProvider.generateToken(authentication);
		
		return new AuthenticationResponse(authenticationToken, loginRequest);
	}
	
	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken(user, token);
		verificationTokenRepository.save(verificationToken);
		
		return token;
	}

	private void fetchUserAndEnable(VerificationToken verificationToken) {
		String username = verificationToken.getUser().getUsername();
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException("User with name " + username + " not found!"));
		
		user.setEnabled(true);
		userRepository.save(user);
	}
	
}
