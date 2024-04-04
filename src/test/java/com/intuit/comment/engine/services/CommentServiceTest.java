package com.intuit.comment.engine.services;

import com.intuit.comment.engine.dao.CommentRepository;
import com.intuit.comment.engine.dto.request.CommentRequestDto;
import com.intuit.comment.engine.dto.request.CommentUpdateRequestDto;
import com.intuit.comment.engine.dto.response.CommentResponseDto;
import com.intuit.comment.engine.entities.CommentEntity;
import com.intuit.comment.engine.entities.UserEntity;
import com.intuit.comment.engine.enums.Status;
import com.intuit.comment.engine.exception.NoContentException;
import com.intuit.comment.engine.exception.ResourceNotFoundException;
import com.intuit.comment.engine.exception.UnauthorizedException;
import com.intuit.comment.engine.services.impl.CommentServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;


/**
 * Copyright (C) @Himanshu - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * <p>
 * Author : Himanshu Mahajan
 * Created On : 02 Apr, 2024 (Tue)
 */

@SpringBootTest
class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentServiceImpl;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    ModelMapper mapper;

    String authorId;
    String nonAuthorId;
    String parentId;
    String commentId;
    String deletedCommentId;
    String replyId;
    String text;
    String replyText;
    String updateText;
    int pageNumber;
    Pageable pageRequest;
    CommentRequestDto commentRequestDto;
    CommentUpdateRequestDto commentUpdateRequestDto;
    CommentResponseDto commentResponseDto;
    CommentResponseDto replyResponseDto;
    CommentEntity commentEntity;
    CommentEntity deletedCommentEntity;
    CommentEntity replyCommentEntity;

    @BeforeEach
    void setUp() {
        authorId = "c763d7d4-405c-430e-a42a-59aecd6e6f91";
        nonAuthorId = "bc8cbd2d-3751-40a0-a7f3-54f1507cc938";
        commentId = "07cbaaea-63e1-42d1-a473-46c515f67887";
        deletedCommentId = "3c338a9a-aa4f-4394-93b6-e1af21d8e27b";
        parentId = "de87eaed-7ef5-463c-bbe9-a799d6ec441a";
        text = "Comment on a Post";
        updateText = "Updated Comment";
        replyText = "Reply on a Comment";
        pageNumber = 0;
        pageRequest = PageRequest.of(pageNumber, 20);
        commentRequestDto = CommentRequestDto.builder().parentId(parentId).text(text).build();
        commentResponseDto = CommentResponseDto.builder().parentId(parentId).text(text).build();
        replyResponseDto = CommentResponseDto.builder().parentId(commentId).text(replyText).build();
        commentUpdateRequestDto = CommentUpdateRequestDto.builder().text(updateText).build();
        commentEntity = CommentEntity.builder()
                .id(commentId)
                .parentId(parentId)
                .user(UserEntity.builder().id(authorId).build())
                .text(text)
                .createdAt(new Date())
                .status(Status.ACTIVE.getValue())
                .build();
        deletedCommentEntity = CommentEntity.builder()
                .id(deletedCommentId)
                .parentId(parentId)
                .user(UserEntity.builder().id(authorId).build())
                .text(text)
                .createdAt(new Date())
                .status(Status.DELETED.getValue())
                .build();
        replyCommentEntity = CommentEntity.builder()
                .id(replyId)
                .parentId(commentId)
                .user(UserEntity.builder().id(authorId).build())
                .text(replyText)
                .createdAt(new Date())
                .status(Status.ACTIVE.getValue())
                .build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createCommentTest() {
        when(commentRepository.save(commentEntity)).thenReturn(commentEntity);
        when(mapper.map(commentRequestDto, CommentEntity.class)).thenReturn(commentEntity);
        when(mapper.map(commentEntity, CommentResponseDto.class)).thenReturn(commentResponseDto);
        CommentResponseDto comment = commentServiceImpl.createComment(authorId, commentRequestDto);
        assertThat(comment).isEqualTo(commentResponseDto);
    }

    @Test
    void updateCommentSuccessTest() {
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(commentEntity));
        when(commentRepository.updateComment(commentId, updateText)).thenReturn(1);
        when(mapper.map(commentEntity, CommentResponseDto.class)).thenReturn(commentResponseDto);
        CommentResponseDto updatedComment = commentServiceImpl.updateComment(commentId, authorId, commentUpdateRequestDto);
        assertThat(updatedComment).isEqualTo(commentResponseDto);
    }

    @Test
    void updateCommentUnauthorizedTest() {
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(commentEntity));
        assertThatExceptionOfType(UnauthorizedException.class)
                .isThrownBy(() -> commentServiceImpl.updateComment(commentId, nonAuthorId, commentUpdateRequestDto))
                .withMessage("User is not authorized to update the comment");
    }

    @Test
    void updateCommentNotFoundTest() {
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> commentServiceImpl.updateComment(commentId, authorId, commentUpdateRequestDto))
                .withMessage("Comment not found with id: " + commentId);
    }

    @Test
    void getCommentFoundTest() {
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(commentEntity));
        when(mapper.map(commentEntity, CommentResponseDto.class)).thenReturn(commentResponseDto);
        CommentResponseDto comment = commentServiceImpl.getComment(commentId);
        assertThat(comment).isEqualTo(commentResponseDto);
    }

    @Test
    void getCommentNotFoundTest() {
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> commentServiceImpl.getComment(commentId))
                .withMessage("Comment not found with id: " + commentId);
    }

    @Test
    void getCommentsFoundTest() {
        when(commentRepository.findAll(pageRequest)).thenReturn(new PageImpl<>(List.of(commentEntity)));
        when(mapper.map(commentEntity, CommentResponseDto.class)).thenReturn(commentResponseDto);
        List<CommentResponseDto> comments = commentServiceImpl.getComments(pageNumber);
        assertThat(comments).isEqualTo(List.of(commentResponseDto));
    }

    @Test
    void getCommentsNotFoundTest() {
        when(commentRepository.findAll(pageRequest)).thenReturn(new PageImpl<>(Collections.emptyList()));
        assertThatExceptionOfType(NoContentException.class)
                .isThrownBy(() -> commentServiceImpl.getComments(pageNumber))
                .withMessage("No comments found");
    }

    @Test
    void getCommentsOnAPostFoundTest() {
        when(commentRepository.findAllByParentIdAndStatus(parentId, Status.ACTIVE.getValue(), pageRequest)).thenReturn(new PageImpl<>(List.of(commentEntity)));
        when(mapper.map(commentEntity, CommentResponseDto.class)).thenReturn(commentResponseDto);
        List<CommentResponseDto> comments = commentServiceImpl.getCommentsOnAPost(parentId, pageNumber);
        assertThat(comments).isEqualTo(List.of(commentResponseDto));
    }

    @Test
    void getCommentsOnAPostNotFoundTest() {
        when(commentRepository.findAllByParentIdAndStatus(parentId, Status.ACTIVE.getValue(), pageRequest)).thenReturn(new PageImpl<>(Collections.emptyList()));
        assertThatExceptionOfType(NoContentException.class)
                .isThrownBy(() -> commentServiceImpl.getCommentsOnAPost(parentId, pageNumber))
                .withMessage("No comments found");
    }

    @Test
    void getRepliesOnACommentFoundTest() {
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(commentEntity));
        when(commentRepository.findAllByParentIdAndStatus(commentId, Status.ACTIVE.getValue(), pageRequest)).thenReturn(new PageImpl<>(List.of(replyCommentEntity)));
        when(mapper.map(replyCommentEntity, CommentResponseDto.class)).thenReturn(replyResponseDto);
        List<CommentResponseDto> comments = commentServiceImpl.getCommentsOnAPost(commentId, pageNumber);
        assertThat(comments).isEqualTo(List.of(replyResponseDto));
    }

    @Test
    void getRepliesOnACommentNotFoundTest() {
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(commentEntity));
        when(commentRepository.findAllByParentIdAndStatus(commentId, Status.ACTIVE.getValue(), pageRequest)).thenReturn(new PageImpl<>(Collections.emptyList()));
        assertThatExceptionOfType(NoContentException.class)
                .isThrownBy(() -> commentServiceImpl.getRepliesOnAComment(commentId, pageNumber))
                .withMessage("No comments found");
    }

    @Test
    void getRepliesOnANotFoundCommentTest() {
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> commentServiceImpl.getRepliesOnAComment(commentId, pageNumber))
                .withMessage("Comment not found with id: " + commentId);
    }

    @Test
    void getRepliesOnDeletedCommentTest() {
        when(commentRepository.findById(deletedCommentId)).thenReturn(Optional.of(deletedCommentEntity));
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> commentServiceImpl.getRepliesOnAComment(deletedCommentId, pageNumber))
                .withMessage("Comment not found with id: " + deletedCommentId);
    }

    @Test
    void softDeleteComment() {
    }
}