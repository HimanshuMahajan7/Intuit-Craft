package com.intuit.comment.engine.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

/**
 * Copyright (C) @Himanshu - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * <p>
 * Author : Himanshu Mahajan
 * Created On : 17 Mar, 2024 (Sun)
 */

@Data
@Entity
@Table(name = "comments")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull(message = "Parent Id is Mandatory")
    @JsonProperty("parent_id")
    private String parentId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Size(max = 200, min = 3, message = "Comment must be min 3 and max 200 character long")
    @NotBlank(message = "Blank Comments are not allowed")
    private String text;

    @JsonProperty("comment_level")
    private int commentLevel;

    @JsonProperty("reply_count")
    private int replyCount = 0;

    @JsonProperty("like_count")
    private int likeCount = 0;

    @JsonProperty("dislike_count")
    private int dislikeCount = 0;

    @JsonProperty("created_at")
    @Column(columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private Date createdAt = new Date();

    @JsonProperty("modified_at")
    private Date modifiedAt;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private short status = 1;
    
}
