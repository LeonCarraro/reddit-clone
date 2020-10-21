package com.leoncarraro.redditclone.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "tb_refresh_token")
@Getter
@Setter
@EqualsAndHashCode
public class RefreshToken implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Instant createdDate;

    public RefreshToken() {
    }

    public RefreshToken(String token, Instant createdDate) {
        this.token = token;
        this.createdDate = createdDate;
    }

}
