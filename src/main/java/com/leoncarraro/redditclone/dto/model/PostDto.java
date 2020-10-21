package com.leoncarraro.redditclone.dto.model;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.github.marlonlom.utilities.timeago.TimeAgoMessages;
import com.leoncarraro.redditclone.model.Post;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Locale;

@Getter
@Setter
public class PostDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String content;
    private Integer voteCount;
    private Integer commentCount;
    private String created;
    private String url;
    private String createdBy;
    private String subredditName;

    public PostDto(Post post) {
        id = post.getId();
        title = post.getTitle();
        content = post.getContent();
        voteCount = post.getVoteCount();
        commentCount = post.getComments().size();
        created = TimeAgo.using(post.getCreatedDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                new TimeAgoMessages.Builder().withLocale(Locale.forLanguageTag("en")).build());
        url = post.getUrl();
        createdBy = post.getUser() == null ? "Unknown" : post.getUser().getUsername();
        subredditName = post.getSubreddit().getName();
    }

}
