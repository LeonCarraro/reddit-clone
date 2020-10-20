package com.leoncarraro.redditclone.service;

import com.leoncarraro.redditclone.dto.model.SubredditCreateDto;
import com.leoncarraro.redditclone.dto.model.SubredditDto;
import com.leoncarraro.redditclone.model.Subreddit;
import com.leoncarraro.redditclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubredditService {

    private final SubredditRepository subredditRepository;

    @Transactional
    public SubredditDto save(SubredditCreateDto subredditCreateDto) {
        Subreddit subreddit = new Subreddit(subredditCreateDto);
        subreddit = subredditRepository.save(subreddit);
        return toDto(subreddit);
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> findAll() {
        return toDto(subredditRepository.findAll());
    }

    private SubredditDto toDto(Subreddit subreddit) {
        return new SubredditDto(subreddit);
    }

    private List<SubredditDto> toDto(List<Subreddit> subreddits) {
        return subreddits.stream().map(s -> new SubredditDto(s)).collect(Collectors.toList());
    }

}
