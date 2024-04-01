package com.intuit.comment.engine.services.impl;

import com.intuit.comment.engine.dao.PostRepository;
import com.intuit.comment.engine.dto.request.PostRequestDto;
import com.intuit.comment.engine.dto.response.PostResponseDto;
import com.intuit.comment.engine.entities.PostEntity;
import com.intuit.comment.engine.entities.UserEntity;
import com.intuit.comment.engine.exception.NoContentException;
import com.intuit.comment.engine.exception.ResourceNotFoundException;
import com.intuit.comment.engine.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Copyright (C) @Himanshu - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * <p>
 * Author : Himanshu Mahajan
 * Created On : 17 Mar, 2024 (Sun)
 */

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public PostResponseDto createPost(String authorId, PostRequestDto postRequestDto) {
        PostEntity post = mapper.map(postRequestDto, PostEntity.class);
        post.setUser(UserEntity.builder().id(authorId).build());
        PostEntity newPost = postRepository.save(post);
        return mapper.map(newPost, PostResponseDto.class);
    }

    @Override
    public PostResponseDto getPost(String postId) {
        Optional<PostEntity> postEntity = postRepository.findById(postId);
        if (postEntity.isPresent() && postEntity.get().getStatus() == 1) {
            return mapper.map(postEntity.get(), PostResponseDto.class);
        }
        throw new ResourceNotFoundException("Post not found with id: " + postId);
    }

    @Override
    public List<PostResponseDto> getPosts(int page) {
        // TODO: Keep this pageSize: 20 in config file
        Pageable pageRequest = PageRequest.of(page, 20);
        Page<PostEntity> posts = postRepository.findAll(pageRequest);
        if (posts.isEmpty()) {
            throw new NoContentException("No posts found");
        }
        return posts.stream().map(postEntity -> mapper.map(postEntity, PostResponseDto.class)).toList();
    }
}
