package com.leoncarraro.redditclone.service;

import com.leoncarraro.redditclone.dto.model.CommentCreateDto;
import com.leoncarraro.redditclone.dto.model.CommentDto;
import com.leoncarraro.redditclone.model.Comment;
import com.leoncarraro.redditclone.model.Post;
import com.leoncarraro.redditclone.model.User;
import com.leoncarraro.redditclone.repository.CommentRepository;
import com.leoncarraro.redditclone.repository.PostRepository;
import com.leoncarraro.redditclone.repository.UserRepository;
import com.leoncarraro.redditclone.service.exception.BadRequestException;
import com.leoncarraro.redditclone.service.exception.PostNotFoundException;
import com.leoncarraro.redditclone.service.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private final AuthService authService;

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<CommentDto> getAllComentsByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with name " + username + " not found!"));

        return toDto(commentRepository.findAllByUser(user));
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getAllComentsByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found! ID: " + postId));

        return toDto(commentRepository.findAllByPost(post));
    }

    @Transactional
    public CommentDto createComment(CommentCreateDto commentCreateDto) {
        User user = authService.getCurrentUser();
        Post post = postRepository.findById(commentCreateDto.getPostId())
                .orElseThrow(() -> new BadRequestException("Post not found! ID: " + commentCreateDto.getPostId()));

        Comment comment = new Comment(commentCreateDto, user, post);

        comment = commentRepository.save(comment);

        return toDto(comment);
    }

    private CommentDto toDto(Comment comment) {
        return new CommentDto(comment);
    }

    private List<CommentDto> toDto(List<Comment> comments) {
        return comments.stream().map(c -> new CommentDto(c)).collect(Collectors.toList());
    }

}
