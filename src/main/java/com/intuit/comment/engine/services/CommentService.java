package com.intuit.comment.engine.services;

import com.intuit.comment.engine.dto.request.CommentRequestDto;
import com.intuit.comment.engine.dto.request.CommentUpdateRequestDto;
import com.intuit.comment.engine.dto.response.CommentResponseDto;

import java.util.List;

/**
 * Copyright (C) @Himanshu - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * <p>
 * Author : Himanshu Mahajan
 * Created On : 17 Mar, 2024 (Sun)
 */

public interface CommentService {

    CommentResponseDto createComment(String authorId, CommentRequestDto comment);

    CommentResponseDto updateComment(String commentId, String authorId, CommentUpdateRequestDto comment);

    CommentResponseDto getComment(String commentId);

    List<CommentResponseDto> getComments(int page);

    List<CommentResponseDto> getCommentsOnAPost(String postId, int page);

    List<CommentResponseDto> getRepliesOnAComment(String commentId, int page);

    boolean softDeleteComment(String commentId);

}
