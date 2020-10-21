package com.leoncarraro.redditclone.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private String authenticationToken;
	private String refreshToken;
	private LocalDateTime expiresAt;
	private String username;
	
	public AuthenticationResponse(String authenticationToken, String refreshToken, LocalDateTime expiresAt, LoginRequest loginRequest) {
		this.authenticationToken = authenticationToken;
		this.refreshToken = refreshToken;
		this.expiresAt = expiresAt;
		username = loginRequest.getUsername();
	}

	public AuthenticationResponse(String authenticationToken, String refreshToken, LocalDateTime expiresAt, String username) {
		this.authenticationToken = authenticationToken;
		this.refreshToken = refreshToken;
		this.expiresAt = expiresAt;
		this.username = username;
	}
	
}
