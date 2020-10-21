package com.leoncarraro.redditclone.controller;

import com.leoncarraro.redditclone.dto.model.CommentCreateDto;
import com.leoncarraro.redditclone.dto.model.CommentDto;
import com.leoncarraro.redditclone.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @RequestMapping(method = RequestMethod.GET, value = "/by-user/{name}")
    public ResponseEntity<List<CommentDto>> getAllComentsByUser(@PathVariable String name) {
        return ResponseEntity.ok(commentService.getAllComentsByUser(name));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/by-post/{id}")
    public ResponseEntity<List<CommentDto>> getAllComentsByPost(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getAllComentsByPost(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentCreateDto commentCreateDto) {
        CommentDto commentDto = commentService.createComment(commentCreateDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(commentDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(commentDto);
    }

}
