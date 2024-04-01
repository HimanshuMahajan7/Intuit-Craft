package com.intuit.comment.engine.services;

import com.intuit.comment.engine.dto.request.UserLoginDto;
import com.intuit.comment.engine.dto.request.UserRegisterDto;
import com.intuit.comment.engine.dto.response.UserResponseDto;

/**
 * Copyright (C) @Himanshu - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * <p>
 * Author : Himanshu Mahajan
 * Created On : 17 Mar, 2024 (Sun)
 */

public interface UserService {

    UserResponseDto registerUser(UserRegisterDto userRegisterDto);

    UserResponseDto login(UserLoginDto userLoginDto);

    UserResponseDto getUser(String userId);

}
