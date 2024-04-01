package com.intuit.comment.engine.exception;

/**
 * Copyright (C) @Himanshu - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * <p>
 * Author : Himanshu Mahajan
 * Created On : 21 Mar, 2024 (Thu)
 */

public class NoContentException extends RuntimeException {
    public NoContentException(String errorMessage) {
        super(errorMessage);
    }
}
