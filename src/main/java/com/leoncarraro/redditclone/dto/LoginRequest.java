package com.leoncarraro.redditclone.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	
}
