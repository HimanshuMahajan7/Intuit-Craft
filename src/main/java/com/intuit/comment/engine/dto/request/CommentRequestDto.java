package com.intuit.comment.engine.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class CommentRequestDto {

    @NotNull(message = "Parent Id is Mandatory")
    @JsonProperty("parent_id")
    private String parentId;

    @Size(max = 200, min = 3, message = "Comment must be min 3 and max 200 character long")
    @NotBlank(message = "Blank Comments are not allowed")
    private String text;

}
