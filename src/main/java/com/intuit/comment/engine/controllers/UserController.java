package com.intuit.comment.engine.controllers;

import com.intuit.comment.engine.dto.request.UserLoginDto;
import com.intuit.comment.engine.dto.request.UserRegisterDto;
import com.intuit.comment.engine.dto.response.ApiResponse;
import com.intuit.comment.engine.dto.response.UserResponseDto;
import com.intuit.comment.engine.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Copyright (C) @Himanshu - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * <p>
 * Author : Himanshu Mahajan
 * Created On : 17 Mar, 2024 (Sun)
 */

@RestController
@RequestMapping("v1/users")
@Tag(name = "User Controller", description = "APIs related to User")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register User", description = "Creates a new User")
    public ResponseEntity<ApiResponse<UserResponseDto>> registerUser(@Valid @RequestBody UserRegisterDto userRequest) {
        UserResponseDto userResponseDto = userService.registerUser(userRequest);
        ApiResponse<UserResponseDto> response = new ApiResponse<>(HttpStatus.CREATED, true, "User Created", userResponseDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "Login User", description = "Login a User")
    public ResponseEntity<ApiResponse<UserResponseDto>> login(@Valid @RequestBody UserLoginDto userLoginDto) {
        UserResponseDto userResponseDto = userService.login(userLoginDto);
        ApiResponse<UserResponseDto> response = new ApiResponse<>(HttpStatus.OK, true, "Login Success", userResponseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get User", description = "Get a User by Id")
    public ResponseEntity<ApiResponse<UserResponseDto>> getUser(@Valid @NotBlank @PathVariable String userId) {
        UserResponseDto userResponseDto = userService.getUser(userId);
        ApiResponse<UserResponseDto> response = new ApiResponse<>(HttpStatus.OK, true, "Success", userResponseDto);
        return ResponseEntity.ok(response);
    }
}