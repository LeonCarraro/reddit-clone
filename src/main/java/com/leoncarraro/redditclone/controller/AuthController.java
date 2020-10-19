package com.leoncarraro.redditclone.controller;

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
		AuthenticationResponse authenticationResponse = authService.login(loginRequest);
		return ResponseEntity.ok(authenticationResponse);
	}
	
}
