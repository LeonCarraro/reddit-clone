package com.leoncarraro.redditclone.dto.model;

import com.leoncarraro.redditclone.model.Comment;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String content;
    private LocalDateTime createdDate;
    private Long postId;
    private String createdBy;

    public CommentDto(Comment comment) {
        id = comment.getId();
        content = comment.getContent();
        createdDate = comment.getCreatedDate();
        postId = comment.getPost().getId();
        createdBy = comment.getUser() == null ? "Unknown" : comment.getUser().getUsername();
    }

}
