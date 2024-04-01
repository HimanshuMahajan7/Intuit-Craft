package com.intuit.comment.engine.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class CommentResponseDto {

    private String id;

    @JsonProperty("parent_id")
    private String parentId;

    private UserResponseDto user;

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
    private Date createdAt = new Date();

    @JsonProperty("modified_at")
    private Date modifiedAt;

}
