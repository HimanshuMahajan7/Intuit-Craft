package com.intuit.comment.engine.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Copyright (C) @Himanshu - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * <p>
 * Author : Himanshu Mahajan
 * Created On : 17 Mar, 2024 (Sun)
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto {

    private String id;

    private String name;

    @JsonProperty("profile_pic")
    private String profilePic;
}
