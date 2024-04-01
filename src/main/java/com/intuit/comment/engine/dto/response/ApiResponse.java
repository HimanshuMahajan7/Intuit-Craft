package com.intuit.comment.engine.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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
 * Created On : 19 Mar, 2024 (Tue)
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private int code;
    private HttpStatusCode status;
    private boolean success;
    private String message;
    private T data;

    public ApiResponse(HttpStatusCode status, boolean success, String message, T data) {
        this(status.value(), status, success, message, data);
    }

    public ApiResponse(HttpStatusCode status, String message, T data) {
        this(status.value(), status, true, message, data);
    }

    public ApiResponse(HttpStatusCode status, String message) {
        this(status, message, null);
    }
}
