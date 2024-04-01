package com.intuit.comment.engine.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "posts")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Size(max = 255, min = 3, message = "Post must be min 3 and max 200 character long")
    @NotBlank(message = "Blank Posts are not allowed")
    @JsonProperty("text")
    private String text;

    @JsonProperty("comment_count")
    private int commentCount = 0;

    @JsonProperty("like_count")
    private int likeCount = 0;

    @JsonProperty("dislike_count")
    private int dislikeCount = 0;

    @JsonProperty("created_at")
    @Column(name = "created_at", columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private Date createdAt = new Date();

    @JsonProperty("modified_at")
    private Date modifiedAt;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private short status = 1;
}
