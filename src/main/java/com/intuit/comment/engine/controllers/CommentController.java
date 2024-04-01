package com.intuit.comment.engine.controllers;

import com.intuit.comment.engine.dto.request.CommentRequestDto;
import com.intuit.comment.engine.dto.request.CommentUpdateRequestDto;
import com.intuit.comment.engine.dto.response.ApiResponse;
import com.intuit.comment.engine.dto.response.CommentResponseDto;
import com.intuit.comment.engine.exception.ResourceNotFoundException;
import com.intuit.comment.engine.exception.UnauthorizedException;
import com.intuit.comment.engine.services.CommentService;
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
@RequestMapping("v1/comments")
@Tag(name = "Comment Controller", description = "APIs related to Comment")
public class CommentController {

    @Autowired
    Util util;

    @Autowired
    private CommentService commentService;

    @PostMapping
    @Operation(summary = "Create Comment", description = "Post a Comment")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<ApiResponse<CommentResponseDto>> createComment(
            @Valid @RequestBody CommentRequestDto commentRequestDto
    ) {
        String authorId = util.getUserId().orElseThrow(() -> new UnauthorizedException("Author id not found in auth token"));
        CommentResponseDto commentResponseDto = commentService.createComment(authorId, commentRequestDto);
        ApiResponse<CommentResponseDto> response = new ApiResponse<>(HttpStatus.CREATED, "Comment Created", commentResponseDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{commentId}")
    @Operation(summary = "Update Comment", description = "Update a Comment by Id")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<ApiResponse<CommentResponseDto>> updateComment(
            @Valid @NotBlank @PathVariable String commentId,
            @Valid @RequestBody CommentUpdateRequestDto commentRequestDto
    ) {
        String authorId = util.getUserId().orElseThrow(() -> new UnauthorizedException("Author id not found in auth token"));
        CommentResponseDto commentResponseDto = commentService.updateComment(commentId, authorId, commentRequestDto);
        ApiResponse<CommentResponseDto> response = new ApiResponse<>(HttpStatus.OK, "Comment Updated", commentResponseDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{commentId}")
    @Operation(summary = "Get Comment", description = "Get a Comment by Id")
    public ResponseEntity<ApiResponse<CommentResponseDto>> getComment(@Valid @NotBlank @PathVariable String commentId) {
        CommentResponseDto commentResponseDto = commentService.getComment(commentId);
        ApiResponse<CommentResponseDto> response = new ApiResponse<>(HttpStatus.OK, "Success", commentResponseDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get Comments (Paginated)", description = "Get all Comments page by page")
    public ResponseEntity<ApiResponse<List<CommentResponseDto>>> getComments(@RequestParam(defaultValue = "0") int page) {
        List<CommentResponseDto> comments = commentService.getComments(page);
        ApiResponse<List<CommentResponseDto>> response = new ApiResponse<>(HttpStatus.OK, true, "Success", comments);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postId}/comments")
    @Operation(summary = "Get Comments on a Post (Paginated)", description = "Get all Comments on a Post page by page")
    public ResponseEntity<ApiResponse<List<CommentResponseDto>>> getCommentsOnAPost(
            @Valid @NotBlank @PathVariable String postId,
            @RequestParam(defaultValue = "0") int page
    ) {
        List<CommentResponseDto> comments = commentService.getCommentsOnAPost(postId, page);
        ApiResponse<List<CommentResponseDto>> response = new ApiResponse<>(HttpStatus.OK, "Success", comments);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{commentId}/replies")
    @Operation(summary = "Get Replies on a Comment (Paginated)", description = "Get all Replies on a Post page by page")
    public ResponseEntity<ApiResponse<List<CommentResponseDto>>> getRepliesOnAComment(
            @Valid @NotBlank @PathVariable String commentId,
            @RequestParam(defaultValue = "0") int page
    ) {
        List<CommentResponseDto> comments = commentService.getRepliesOnAComment(commentId, page);
        ApiResponse<List<CommentResponseDto>> response = new ApiResponse<>(HttpStatus.OK, "Success", comments);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "Delete Comment", description = "Delete Comment by Id")
    public ResponseEntity<ApiResponse<Object>> softDeleteComment(@PathVariable String commentId) {
        boolean isUpdated = commentService.softDeleteComment(commentId);
        if (isUpdated) {
            ApiResponse<Object> response = new ApiResponse<>(HttpStatus.OK, "Comment Deleted");
            return ResponseEntity.ok(response);
        }
        throw new ResourceNotFoundException("Comment/Reply not found with id: " + commentId);
    }
}
