package com.leoncarraro.redditclone.dto.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CommentCreateDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String content;
    private Long postId;

}
