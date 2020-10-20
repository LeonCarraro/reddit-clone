package com.leoncarraro.redditclone.controller;

import com.leoncarraro.redditclone.dto.model.PostCreateDto;
import com.leoncarraro.redditclone.dto.model.PostDto;
import com.leoncarraro.redditclone.model.Post;
import com.leoncarraro.redditclone.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok(postService.findAll());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<PostDto> getOnePost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findById(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<PostDto> createPost(@RequestBody PostCreateDto postCreateDto) {
        PostDto PostDto = postService.save(postCreateDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(PostDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(PostDto);
    }

}
