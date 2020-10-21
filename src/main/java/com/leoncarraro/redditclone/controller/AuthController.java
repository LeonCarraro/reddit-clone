package com.leoncarraro.redditclone.controller;

import com.leoncarraro.redditclone.dto.RefreshTokenRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.leoncarraro.redditclone.dto.AuthenticationResponse;
import com.leoncarraro.redditclone.dto.LoginRequest;
import com.leoncarraro.redditclone.dto.RegisterRequest;
import com.leoncarraro.redditclone.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api/auth")
@AllArgsConstructor
public class AuthController {

	private final AuthService authService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/signup")
	public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
		authService.signup(registerRequest);
		return ResponseEntity.ok("User succesfully registered!");
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/accountVerification/{token}")
	public ResponseEntity<String> verifyAccount(@PathVariable String token) {
		authService.verifyAccount(token);
		return ResponseEntity.ok("User succesfully verified!");
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(authService.login(loginRequest));
	}

	@RequestMapping(method = RequestMethod.POST, value = "/refresh-token")
	public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
		return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
	}

	@RequestMapping(method = RequestMethod.POST, value = "/logout")
	public ResponseEntity<String> logout(@RequestBody RefreshTokenRequest refreshTokenRequest) {
		authService.logout(refreshTokenRequest);
		return ResponseEntity.ok("Refresh token succesfully deleted!");
	}
	
}
