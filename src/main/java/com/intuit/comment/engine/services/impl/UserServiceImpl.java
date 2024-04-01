package com.intuit.comment.engine.services.impl;

import com.intuit.comment.engine.auth.JwtService;
import com.intuit.comment.engine.dao.UserRepository;
import com.intuit.comment.engine.dto.request.UserLoginDto;
import com.intuit.comment.engine.dto.request.UserRegisterDto;
import com.intuit.comment.engine.dto.response.UserAuthResponseDto;
import com.intuit.comment.engine.dto.response.UserResponseDto;
import com.intuit.comment.engine.entities.UserEntity;
import com.intuit.comment.engine.exception.ResourceAlreadyExistsException;
import com.intuit.comment.engine.exception.ResourceNotFoundException;
import com.intuit.comment.engine.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Copyright (C) @Himanshu - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * <p>
 * Author : Himanshu Mahajan
 * Created On : 17 Mar, 2024 (Sun)
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationProvider authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper mapper;

    @Override
    public UserResponseDto registerUser(UserRegisterDto userRegisterDto) {

        if (userRepository.findByEmail(userRegisterDto.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("User already registered !!!");
        }

        UserEntity user = mapper.map(userRegisterDto, UserEntity.class);
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        UserEntity savedUser = userRepository.save(user);
        UserAuthResponseDto userResponseDto = mapper.map(savedUser, UserAuthResponseDto.class);

        String token = jwtService.generateToken(userResponseDto);
        userResponseDto.setToken(token);

        return userResponseDto;
    }

    @Override
    public UserResponseDto login(UserLoginDto userLoginDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword()));

        UserEntity userEntity = userRepository.findByEmail(userLoginDto.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found!!!"));
        UserAuthResponseDto userResponseDto = mapper.map(userEntity, UserAuthResponseDto.class);

        String token = jwtService.generateToken(userResponseDto);
        userResponseDto.setToken(token);

        return userResponseDto;
    }

    @Override
    public UserResponseDto getUser(String userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isPresent()) {
            return mapper.map(userEntity.get(), UserResponseDto.class);
        }
        throw new ResourceNotFoundException("User not found with id: " + userId);
    }
}
