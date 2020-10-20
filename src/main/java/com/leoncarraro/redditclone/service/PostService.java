package com.leoncarraro.redditclone.service;

import com.leoncarraro.redditclone.dto.model.PostCreateDto;
import com.leoncarraro.redditclone.dto.model.PostDto;
import com.leoncarraro.redditclone.model.Post;
import com.leoncarraro.redditclone.model.Subreddit;
import com.leoncarraro.redditclone.repository.PostRepository;
import com.leoncarraro.redditclone.repository.SubredditRepository;
import com.leoncarraro.redditclone.service.exception.BadRequestException;
import com.leoncarraro.redditclone.service.exception.PostNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;

    @Transactional(readOnly = true)
    public List<PostDto> findAll() {
        return toDto(postRepository.findAll());
    }

    @Transactional(readOnly = true)
    public PostDto findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found! ID: " + id));
        return toDto(post);
    }

    @Transactional
    public PostDto save(PostCreateDto postCreateDto) {
        Post post = new Post(postCreateDto);

        Subreddit subreddit = subredditRepository.findById(postCreateDto.getSubredditId())
                .orElseThrow(() -> new BadRequestException("Subreddit not found! ID: " +
                        postCreateDto.getSubredditId()));
        post.setSubreddit(subreddit);

        post = postRepository.save(post);
        return toDto(post);
    }

    private PostDto toDto(Post post) {
        return new PostDto(post);
    }

    private List<PostDto> toDto(List<Post> posts) {
        return posts.stream().map(p -> new PostDto(p)).collect(Collectors.toList());
    }

}
