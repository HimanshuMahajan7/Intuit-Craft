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
public class PostResponseDto {

    private String id;

    private UserResponseDto user;

    @JsonProperty("text")
    private String text;

    @JsonProperty("comment_count")
    private int commentCount;

    @JsonProperty("like_count")
    private int likeCount;

    @JsonProperty("dislike_count")
    private int dislikeCount;

    @JsonProperty("created_at")
    private Date createdAt = new Date();

    @JsonProperty("modified_at")
    private Date modifiedAt;

    private short status;

}
