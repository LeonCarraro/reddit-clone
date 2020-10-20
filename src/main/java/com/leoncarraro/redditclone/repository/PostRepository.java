package com.leoncarraro.redditclone.repository;

import com.leoncarraro.redditclone.model.Subreddit;
import com.leoncarraro.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leoncarraro.redditclone.model.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllBySubreddit(Subreddit subreddit);
    List<Post> findAllByUser(User user);

}
