package com.leoncarraro.redditclone.service.exception;

public class InvalidRefreshTokenException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidRefreshTokenException(String msg) {
		super(msg);
	}

}
