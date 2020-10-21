package com.leoncarraro.redditclone.service;

import com.leoncarraro.redditclone.dto.model.VoteCreateDto;
import com.leoncarraro.redditclone.model.Post;
import com.leoncarraro.redditclone.model.User;
import com.leoncarraro.redditclone.model.Vote;
import com.leoncarraro.redditclone.model.VoteType;
import com.leoncarraro.redditclone.repository.PostRepository;
import com.leoncarraro.redditclone.repository.VoteRepository;
import com.leoncarraro.redditclone.service.exception.BadRequestException;
import com.leoncarraro.redditclone.service.exception.RepeatedVoteException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteService {

    private final AuthService authService;

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;

    @Transactional
    public void vote(VoteCreateDto voteCreateDto) {
        // TODO - Validate VoteCount
        User user = authService.getCurrentUser();;
        Post post = postRepository.findById(voteCreateDto.getPostId())
                .orElseThrow(() -> new BadRequestException("Post not found! ID: " + voteCreateDto.getPostId()));

        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByIdDesc(post, user);

        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteCreateDto.getVoteType())) {
            throw new RepeatedVoteException("You have already " + voteCreateDto.getVoteType() + "D to this post!");
        }

        if (voteCreateDto.getVoteType().equals(VoteType.UPVOTE)) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }

        Vote vote = new Vote(voteCreateDto, post, user);

        vote = voteRepository.save(vote);
        post = postRepository.save(post);
    }

}
