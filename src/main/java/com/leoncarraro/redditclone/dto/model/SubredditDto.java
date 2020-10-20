package com.leoncarraro.redditclone.dto.model;

import com.leoncarraro.redditclone.model.Subreddit;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class SubredditDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdDate;

    public SubredditDto(Subreddit subreddit) {
        id = subreddit.getId();
        name = subreddit.getName();
        description = subreddit.getDescription();
        createdDate = subreddit.getCreatedDate();
    }

}
