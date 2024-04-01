package com.intuit.comment.engine.services.impl;

import com.intuit.comment.engine.dao.CommentRepository;
import com.intuit.comment.engine.dao.PostRepository;
import com.intuit.comment.engine.dto.request.CommentRequestDto;
import com.intuit.comment.engine.dto.request.CommentUpdateRequestDto;
import com.intuit.comment.engine.dto.response.CommentResponseDto;
import com.intuit.comment.engine.entities.CommentEntity;
import com.intuit.comment.engine.entities.UserEntity;
import com.intuit.comment.engine.enums.Status;
import com.intuit.comment.engine.exception.NoContentException;
import com.intuit.comment.engine.exception.ResourceNotFoundException;
import com.intuit.comment.engine.exception.UnauthorizedException;
import com.intuit.comment.engine.services.CommentService;
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
public class CommentServiceImpl implements CommentService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public CommentResponseDto createComment(String authorId, CommentRequestDto commentRequestDto) {
        CommentEntity comment = mapper.map(commentRequestDto, CommentEntity.class);
        comment.setUser(UserEntity.builder().id(authorId).build());
        CommentEntity newComment = commentRepository.save(comment);
        return mapper.map(newComment, CommentResponseDto.class);
    }

    @Override
    public CommentResponseDto updateComment(String commentId, String authorId, CommentUpdateRequestDto commentRequestDto) {
        Optional<CommentEntity> commentEntity = commentRepository.findById(commentId);
        if (commentEntity.isPresent()) {
            if (!commentEntity.get().getUser().getId().equals(authorId)) {
                throw new UnauthorizedException("User is not authorized to update the comment");
            }
            int updatedCount = commentRepository.updateComment(commentId, commentRequestDto.getText());
            if (updatedCount != 0) {
                commentEntity.get().setText(commentRequestDto.getText());
            }
            return mapper.map(commentEntity, CommentResponseDto.class);
        }
        throw new ResourceNotFoundException("Comment not found with id: " + commentId);
    }

    @Override
    public CommentResponseDto getComment(String commentId) {
        Optional<CommentEntity> commentEntity = commentRepository.findById(commentId);
        if (commentEntity.isPresent()) {
            return mapper.map(commentEntity.get(), CommentResponseDto.class);
        }
        throw new ResourceNotFoundException("Comment not found with id: " + commentId);
    }

    @Override
    public List<CommentResponseDto> getComments(int page) {
        // TODO: Keep this pageSize: 20 in config file
        Pageable pageRequest = PageRequest.of(page, 20);
        Page<CommentEntity> comments = commentRepository.findAll(pageRequest);
        if (comments.isEmpty()) {
            throw new NoContentException("No comments found");
        }
        return comments.stream().map(commentEntity -> mapper.map(commentEntity, CommentResponseDto.class)).toList();
    }

    @Override
    public List<CommentResponseDto> getCommentsOnAPost(String postId, int page) {
        return getComments(postId, page);
    }

    @Override
    public List<CommentResponseDto> getRepliesOnAComment(String commentId, int page) {
        Optional<CommentEntity> commentEntity = commentRepository.findById(commentId);
        if (commentEntity.isPresent() && commentEntity.get().getStatus() == Status.ACTIVE.getValue()) {
            return getComments(commentId, page);
        }
        throw new ResourceNotFoundException("Comment not found with id: " + commentId);
    }

    private List<CommentResponseDto> getComments(String parentId, int page) {
        // TODO: Keep this pageSize: 20 in config file
        Pageable pageRequest = PageRequest.of(page, 20);
        Page<CommentEntity> comments = commentRepository.findAllByParentIdAndStatus(parentId, Status.ACTIVE.getValue(), pageRequest);
        if (comments.isEmpty()) {
            throw new NoContentException("No comments found");
        }
        return comments.stream().map(commentEntity -> mapper.map(commentEntity, CommentResponseDto.class)).toList();
    }

    @Override
    public boolean softDeleteComment(String commentId) {
        int updatedCount = commentRepository.softDeleteComment(commentId);
        return updatedCount != 0;
    }
}
