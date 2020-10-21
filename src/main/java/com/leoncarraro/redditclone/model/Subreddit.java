package com.leoncarraro.redditclone.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.leoncarraro.redditclone.dto.model.SubredditCreateDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_subreddit")
@Getter
@Setter
@EqualsAndHashCode
public class Subreddit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "subreddit", fetch = FetchType.LAZY)
	private List<Post> posts;
	
	private String name;
	private String description;
	private LocalDateTime createdDate;

	public Subreddit() {
	}

	public Subreddit(SubredditCreateDto subredditCreateDto) {
		name = subredditCreateDto.getName();
		posts = new ArrayList<>();
		description = subredditCreateDto.getDescription();
		createdDate = LocalDateTime.now();
	}

}
