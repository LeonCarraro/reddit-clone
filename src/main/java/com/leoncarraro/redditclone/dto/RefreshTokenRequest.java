package com.leoncarraro.redditclone.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RefreshTokenRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String refreshToken;
    private String username;

}
