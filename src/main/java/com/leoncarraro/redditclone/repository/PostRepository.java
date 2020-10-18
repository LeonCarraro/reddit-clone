package com.leoncarraro.redditclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leoncarraro.redditclone.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
