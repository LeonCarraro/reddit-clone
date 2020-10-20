package com.leoncarraro.redditclone.service.exception;

public class SubredditNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public SubredditNotFoundException(String msg) {
        super(msg);
    }

}
