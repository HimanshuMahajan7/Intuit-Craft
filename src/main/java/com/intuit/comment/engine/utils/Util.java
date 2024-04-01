package com.intuit.comment.engine.utils;

import com.intuit.comment.engine.dto.response.UserAuthResponseDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Copyright (C) @Himanshu - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * <p>
 * Author : Himanshu Mahajan
 * Created On : 26 Mar, 2024 (Tue)
 */

@Component
public class Util {
    public Optional<String> getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAuthResponseDto userAuthResponseDto = (UserAuthResponseDto) authentication.getPrincipal();
        String userId = userAuthResponseDto.getId();
        return Optional.ofNullable(userId);
    }
}
