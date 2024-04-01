package com.intuit.comment.engine.utils;

import com.intuit.comment.engine.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;

/**
 * Copyright (C) @Himanshu - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * <p>
 * Author : Himanshu Mahajan
 * Created On : 21 Mar, 2024 (Thu)
 */

public class ResponseUtil {

    public static ApiResponse<Object> formApiResponse(HttpStatus status, boolean success, String message, Object data) {
        return ApiResponse
                .builder()
                .code(status.value())
                .status(status)
                .success(success)
                .message(message)
                .data(data)
                .build();
    }
}
