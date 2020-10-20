package com.leoncarraro.redditclone.service;

import com.leoncarraro.redditclone.dto.model.PostCreateDto;
import com.leoncarraro.redditclone.dto.model.PostDto;
import com.leoncarraro.redditclone.model.Post;
import com.leoncarraro.redditclone.model.Subreddit;
import com.leoncarraro.redditclone.model.User;
import com.leoncarraro.redditclone.repository.PostRepository;
import com.leoncarraro.redditclone.repository.SubredditRepository;
import com.leoncarraro.redditclone.repository.UserRepository;
import com.leoncarraro.redditclone.service.exception.BadRequestException;
import com.leoncarraro.redditclone.service.exception.PostNotFoundException;
import com.leoncarraro.redditclone.service.exception.SubredditNotFoundException;
import com.leoncarraro.redditclone.service.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<PostDto> getAllPosts() {
        return toDto(postRepository.findAll());
    }

    @Transactional(readOnly = true)
    public PostDto getOnePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found! ID: " + id));

        return toDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostDto> getAllPostsBySubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SubredditNotFoundException("Subreddit not found! ID: " + id));

        return toDto(postRepository.findAllBySubreddit(subreddit));
    }

    @Transactional(readOnly = true)
    public List<PostDto> getAllPostsByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with name " + username + " not found!"));

        return toDto(postRepository.findAllByUser(user));
    }

    @Transactional
    public PostDto createPost(PostCreateDto postCreateDto) {
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
