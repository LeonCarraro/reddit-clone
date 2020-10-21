package com.leoncarraro.redditclone.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.leoncarraro.redditclone.dto.model.PostCreateDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_post")
@Getter
@Setter
@EqualsAndHashCode
public class Post implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subreddit_id")
	private Subreddit subreddit;

	@OneToMany(mappedBy = "post")
	private List<Comment> comments;
	
	private String title;
	@Lob
	private String content;
	private Integer voteCount;
	private LocalDateTime createdDate;
	private String url;

	public Post() {
	}

	public Post(PostCreateDto post, User user, Subreddit subreddit) {
		this.user = user;
		this.subreddit = subreddit;
		comments = new ArrayList<>();
		title = post.getTitle();
		content = post.getContent();
		voteCount = 0;
		createdDate = LocalDateTime.now();
		url = post.getUrl();
	}

}
