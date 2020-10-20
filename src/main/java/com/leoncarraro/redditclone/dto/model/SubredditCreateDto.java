package com.leoncarraro.redditclone.dto.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SubredditCreateDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String description;

}
