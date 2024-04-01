package com.intuit.comment.engine.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

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
public class ExceptionDto {
    private int code;
    @JsonProperty("status_code")
    private HttpStatusCode statusCode;
    private boolean success;
    private String message;
    private Object error;

    public ExceptionDto(HttpStatusCode status, boolean success, String message, Object error) {
        this(status.value(), status, success, message, error);
    }

    public ExceptionDto(HttpStatusCode status, String message, Object error) {
        this(status.value(), status, false, message, error);
    }
}
