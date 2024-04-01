package com.intuit.comment.engine.controllers;

import com.intuit.comment.engine.dto.request.PostRequestDto;
import com.intuit.comment.engine.dto.response.ApiResponse;
import com.intuit.comment.engine.dto.response.PostResponseDto;
import com.intuit.comment.engine.exception.UnauthorizedException;
import com.intuit.comment.engine.services.PostService;
import com.intuit.comment.engine.utils.Util;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Copyright (C) @Himanshu - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * <p>
 * Author : Himanshu Mahajan
 * Created On : 17 Mar, 2024 (Sun)
 */

@Slf4j
@RestController
@RequestMapping("v1/posts")
@Tag(name = "Post Controller", description = "APIs related to Post")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    Util util;

    @PostMapping
    @Operation(summary = "Create Post", description = "Creates a Post")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<ApiResponse<PostResponseDto>> createPost(
            @Valid @RequestBody PostRequestDto postRequestDto
    ) {
        String userId = util.getUserId().orElseThrow(() -> new UnauthorizedException("Author id not found in auth token"));
        PostResponseDto postResponseDto = postService.createPost(userId, postRequestDto);
        ApiResponse<PostResponseDto> response = new ApiResponse<>(HttpStatus.CREATED, true, "Post Created", postResponseDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    @Operation(summary = "Get Post", description = "Get a Post by Id")
    public ResponseEntity<ApiResponse<PostResponseDto>> getPost(@Valid @NotBlank @PathVariable String postId) {
        PostResponseDto postResponseDto = postService.getPost(postId);
        ApiResponse<PostResponseDto> response = new ApiResponse<>(HttpStatus.OK, true, "Success", postResponseDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all Posts (Paginated)", description = "Get all Posts page by page")
    public ResponseEntity<ApiResponse<List<PostResponseDto>>> getPosts(@RequestParam(defaultValue = "0") int page) {
        List<PostResponseDto> posts = postService.getPosts(page);
        ApiResponse<List<PostResponseDto>> response = new ApiResponse<>(HttpStatus.OK, true, "Success", posts);
        return ResponseEntity.ok(response);
    }

}
