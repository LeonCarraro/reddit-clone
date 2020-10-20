package com.leoncarraro.redditclone.service;

import com.leoncarraro.redditclone.dto.model.SubredditCreateDto;
import com.leoncarraro.redditclone.dto.model.SubredditDto;
import com.leoncarraro.redditclone.model.Subreddit;
import com.leoncarraro.redditclone.repository.SubredditRepository;
import com.leoncarraro.redditclone.service.exception.SubredditNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubredditService {

    private final SubredditRepository subredditRepository;

    @Transactional(readOnly = true)
    public List<SubredditDto> getAllSubreddits() {
        return toDto(subredditRepository.findAll());
    }

    @Transactional(readOnly = true)
    public SubredditDto getOneSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SubredditNotFoundException("Subreddit not found! ID: " + id));
        return toDto(subreddit);
    }

    @Transactional
    public SubredditDto createSubreddit(SubredditCreateDto subredditCreateDto) {
        Subreddit subreddit = new Subreddit(subredditCreateDto);
        subreddit = subredditRepository.save(subreddit);
        return toDto(subreddit);
    }

    private SubredditDto toDto(Subreddit subreddit) {
        return new SubredditDto(subreddit);
    }

    private List<SubredditDto> toDto(List<Subreddit> subreddits) {
        return subreddits.stream().map(s -> new SubredditDto(s)).collect(Collectors.toList());
    }

}
