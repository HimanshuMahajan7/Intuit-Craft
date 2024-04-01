package com.intuit.comment.engine.services;

import com.intuit.comment.engine.dto.request.PostRequestDto;
import com.intuit.comment.engine.dto.response.PostResponseDto;

import java.util.List;

/**
 * Copyright (C) @Himanshu - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * <p>
 * Author : Himanshu Mahajan
 * Created On : 17 Mar, 2024 (Sun)
 */

public interface PostService {
    PostResponseDto createPost(String authorId, PostRequestDto postRequestDto);

    PostResponseDto getPost(String postId);

    List<PostResponseDto> getPosts(int page);
}
