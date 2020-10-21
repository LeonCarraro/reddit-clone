package com.leoncarraro.redditclone.service.exception;

public class RepeatedVoteException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RepeatedVoteException(String msg) {
        super(msg);
    }

}
