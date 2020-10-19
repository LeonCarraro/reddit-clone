package com.leoncarraro.redditclone.service.exception;

public class KeyStoreLoadException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public KeyStoreLoadException(String msg) {
		super(msg);
	}

}
