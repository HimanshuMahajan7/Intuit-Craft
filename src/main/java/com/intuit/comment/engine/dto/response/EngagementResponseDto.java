package com.intuit.comment.engine.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.intuit.comment.engine.enums.EngagementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Copyright (C) @Himanshu - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * <p>
 * Author : Himanshu Mahajan
 * Created On : 20 Mar, 2024 (Wed)
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EngagementResponseDto {


    @JsonProperty("resource_id")
    private String resourceId;

//    @JsonProperty("user_id")
//    private String userId;

    private UserResponseDto user;

    @JsonProperty("engagement_type")
    private EngagementType engagementType;

    @JsonProperty("created_at")
    private Date createdAt;

}
