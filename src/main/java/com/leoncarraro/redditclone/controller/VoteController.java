package com.leoncarraro.redditclone.controller;

import com.leoncarraro.redditclone.dto.model.VoteCreateDto;
import com.leoncarraro.redditclone.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/votes")
@AllArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> vote(@RequestBody VoteCreateDto voteCreateDto) {
        voteService.vote(voteCreateDto);
        return ResponseEntity.ok().build();
    }

}
