package com.leoncarraro.redditclone.controller.exception;

import com.leoncarraro.redditclone.service.exception.BadRequestException;
import com.leoncarraro.redditclone.service.exception.PostNotFoundException;
import com.leoncarraro.redditclone.service.exception.SubredditNotFoundException;
import com.leoncarraro.redditclone.service.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = { BadRequestException.class })
    public ResponseEntity<StandardError> badRequestExceptionHandler(BadRequestException e) {
        StandardError error = new StandardError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                HttpStatus.BAD_REQUEST.value(),
                Arrays.asList(e.getMessage()));
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(value = { PostNotFoundException.class,
                                SubredditNotFoundException.class,
                                UserNotFoundException.class})
    public ResponseEntity<StandardError> resourceNotFoundException(RuntimeException e) {
        StandardError error = new StandardError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                HttpStatus.NOT_FOUND.value(),
                Arrays.asList(e.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}
