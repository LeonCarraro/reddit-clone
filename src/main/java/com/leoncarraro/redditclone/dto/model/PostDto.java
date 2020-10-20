package com.leoncarraro.redditclone.dto.model;

import com.leoncarraro.redditclone.model.Post;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class PostDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String content;
    private Integer voteCount;
    private LocalDateTime createdDate;
    private String url;
    private String createdBy;
    private String subredditName;

    public PostDto(Post post) {
        id = post.getId();
        title = post.getTitle();
        content = post.getContent();
        voteCount = post.getVoteCount();
        createdDate = post.getCreatedDate();
        url = post.getUrl();
        createdBy = null;  // TODO Implemnt createdBy
        subredditName = post.getSubreddit().getName();
    }

}
