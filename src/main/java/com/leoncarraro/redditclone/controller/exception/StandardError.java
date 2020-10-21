package com.leoncarraro.redditclone.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class StandardError implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDateTime timestamp;
    private String status;
    private Integer statusCode;
    private List<String> errors;

}
