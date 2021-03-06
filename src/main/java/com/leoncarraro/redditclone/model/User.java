package com.leoncarraro.redditclone.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.leoncarraro.redditclone.dto.RegisterRequest;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_user")
@Getter
@Setter
@EqualsAndHashCode
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Post> posts;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Subreddit> subreddits;
	
	private String username;
	private String password;
	private String email;
	private LocalDateTime createdDate;
	private Boolean enabled;
	
	public User() {
	}
	
	public User(RegisterRequest registerRequest, PasswordEncoder passwordEncoder) {
		username = registerRequest.getUsername();
		password = passwordEncoder.encode(registerRequest.getPassword());
		email = registerRequest.getEmail();
		createdDate = LocalDateTime.now();
		enabled = false;
	}
	
}
