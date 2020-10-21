package com.leoncarraro.redditclone.service;

import java.time.LocalDateTime;
import java.util.UUID;

import com.leoncarraro.redditclone.dto.RefreshTokenRequest;
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
import com.leoncarraro.redditclone.model.UserVerificationToken;
import com.leoncarraro.redditclone.repository.UserRepository;
import com.leoncarraro.redditclone.repository.UserVerificationTokenRepository;
import com.leoncarraro.redditclone.security.JwtProvider;
import com.leoncarraro.redditclone.service.exception.InvalidTokenException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;

	private final MailService mailService;
	private final RefreshTokenService refreshTokenService;

	private final UserRepository userRepository;
	private final UserVerificationTokenRepository userVerificationTokenRepository;
	
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
		UserVerificationToken userVerificationToken = userVerificationTokenRepository.findByToken(token)
				.orElseThrow(() -> new InvalidTokenException("Invalid token!"));
		
		fetchUserAndEnable(userVerificationToken);
	}

	@Transactional(readOnly = true)
	public User getCurrentUser() {
		org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();

		return userRepository.findByUsername(principal.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("User with name " + principal.getUsername() + " not found!"));
	}
	
	public AuthenticationResponse login(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
			loginRequest.getUsername(),
			loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String authenticationToken = jwtProvider.generateToken(authentication);
		Long jwtExpirationTime = jwtProvider.getJwtExpirationTime();

		return new AuthenticationResponse(authenticationToken,
				refreshTokenService.generateRefreshToken().getToken(),
				LocalDateTime.now().plusMinutes(jwtExpirationTime / 60000L),
				loginRequest);
	}

	public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
		refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());

		String token = jwtProvider.generateTokenWithUsername(refreshTokenRequest.getUsername());
		Long jwtExpirationTime = jwtProvider.getJwtExpirationTime();

		return new AuthenticationResponse(token,
				refreshTokenRequest.getRefreshToken(),
				LocalDateTime.now().plusMinutes(jwtExpirationTime / 60000L),
				refreshTokenRequest.getUsername());
	}

	public void logout(RefreshTokenRequest refreshTokenRequest) {
		refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
	}
	
	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		UserVerificationToken userVerificationToken = new UserVerificationToken(user, token);
		userVerificationTokenRepository.save(userVerificationToken);
		
		return token;
	}

	private void fetchUserAndEnable(UserVerificationToken userVerificationToken) {
		String username = userVerificationToken.getUser().getUsername();
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException("User with name " + username + " not found!"));
		
		user.setEnabled(true);
		userRepository.save(user);
	}
	
}
