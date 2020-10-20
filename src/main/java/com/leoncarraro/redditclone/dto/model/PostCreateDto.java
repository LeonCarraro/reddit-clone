package com.leoncarraro.redditclone.dto.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PostCreateDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String content;
    private String url;
    private Long subredditId;

}
