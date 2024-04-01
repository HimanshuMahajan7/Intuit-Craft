package com.intuit.comment.engine.dao;

import com.intuit.comment.engine.entities.CommentEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Copyright (C) @Himanshu - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * <p>
 * Author : Himanshu Mahajan
 * Created On : 17 Mar, 2024 (Sun)
 */

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, String> {

    Page<CommentEntity> findAllByParentIdAndStatus(String commentId, short status, Pageable pageRequest);

    @Modifying
    @Transactional
    @Query("update CommentEntity comment set comment.text = :text where comment.id = :id")
    int updateComment(String id, String text);

    @Modifying
    @Transactional
    @Query("UPDATE CommentEntity comment SET comment.status = 0 WHERE comment.id = :id")
    int softDeleteComment(String id);

}