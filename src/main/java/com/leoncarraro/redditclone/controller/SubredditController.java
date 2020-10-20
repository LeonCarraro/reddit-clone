package com.leoncarraro.redditclone.controller;

import com.leoncarraro.redditclone.dto.model.SubredditCreateDto;
import com.leoncarraro.redditclone.dto.model.SubredditDto;
import com.leoncarraro.redditclone.service.SubredditService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/subreddits")
@AllArgsConstructor
public class SubredditController {

    private final SubredditService subredditService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditCreateDto subredditCreateDto) {
        SubredditDto subredditDto = subredditService.save(subredditCreateDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(subredditDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(subredditDto);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<SubredditDto>> getAllSubreddits() {
        return ResponseEntity.ok(subredditService.findAll());
    }

}
