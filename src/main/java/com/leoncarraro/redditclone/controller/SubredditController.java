package com.leoncarraro.redditclone.controller;

import com.leoncarraro.redditclone.dto.model.SubredditCreateDto;
import com.leoncarraro.redditclone.dto.model.SubredditDto;
import com.leoncarraro.redditclone.service.SubredditService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/subreddits")
@AllArgsConstructor
public class SubredditController {

    private final SubredditService subredditService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<SubredditDto>> getAllSubreddits() {
        return ResponseEntity.ok(subredditService.getAllSubreddits());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<SubredditDto> getOneSubreddit(@PathVariable Long id) {
        return ResponseEntity.ok(subredditService.getOneSubreddit(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditCreateDto subredditCreateDto) {
        SubredditDto subredditDto = subredditService.createSubreddit(subredditCreateDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(subredditDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(subredditDto);
    }

}
