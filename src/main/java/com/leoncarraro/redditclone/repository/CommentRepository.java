package com.leoncarraro.redditclone.repository;

import com.leoncarraro.redditclone.model.Post;
import com.leoncarraro.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leoncarraro.redditclone.model.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost(Post post);
    List<Comment> findAllByUser(User user);

}
