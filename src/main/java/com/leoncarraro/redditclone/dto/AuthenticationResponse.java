package com.leoncarraro.redditclone.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private String authenticationToken;
	private String username;
	
	public AuthenticationResponse(String authenticationToken, LoginRequest loginRequest) {
		this.authenticationToken = authenticationToken;
		username = loginRequest.getUsername();
	}
	
}
