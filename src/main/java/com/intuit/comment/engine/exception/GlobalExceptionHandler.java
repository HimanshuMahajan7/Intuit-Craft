package com.intuit.comment.engine.exception;

import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Copyright (C) @Himanshu - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * <p>
 * Author : Himanshu Mahajan
 * Created On : 20 Mar, 2024 (Wed)
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Object> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(ex.getStatusCode().value(), ex.getStatusCode(), false, "Missing Header", ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(ex.getStatusCode().value(), ex.getStatusCode(), false, "Invalid Request Body", ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, false, "Invalid Request Body", ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, false, "Invalid Params", ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(ex.getStatusCode().value(), ex.getStatusCode(), false, "Resource Not Found", ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleInvalidArguments(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (oldVal, newVal) -> oldVal));

        ExceptionDto exceptionDto = new ExceptionDto(ex.getStatusCode().value(), ex.getStatusCode(), false, "Invalid Params", errors);
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        ExceptionDto exceptionDto = ExceptionDto
                .builder()
                .code(ex.getStatusCode().value())
                .statusCode(ex.getStatusCode())
                .message("Method Not Allowed")
                .error(ex.getMessage())
                .build();
        return new ResponseEntity<>(exceptionDto, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex) {
        ExceptionDto exceptionDto = ExceptionDto
                .builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .statusCode(HttpStatus.UNAUTHORIZED)
                .success(false)
                .message("Unauthorized")
                .error(ex.getMessage())
                .build();
        return new ResponseEntity<>(exceptionDto, HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Object> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(HttpStatus.CONFLICT, "Already Exists", ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(HttpStatus.UNAUTHORIZED, "Invalid Credentials", ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Object> handleJwtException(MalformedJwtException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(HttpStatus.UNAUTHORIZED, "Invalid Token", ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<ExceptionDto> handleNoContentException(NoContentException ex) {
        ExceptionDto exceptionDto = ExceptionDto
                .builder()
                .code(HttpStatus.NO_CONTENT.value())
                .statusCode(HttpStatus.NO_CONTENT)
                .message("No Content Found")
                .error(ex.getMessage())
                .build();
        return new ResponseEntity<>(exceptionDto, HttpStatus.NO_CONTENT);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ExceptionDto exceptionDto = ExceptionDto
                .builder()
                .code(HttpStatus.NOT_FOUND.value())
                .statusCode(HttpStatus.NOT_FOUND)
                .message("Resource Not Found")
                .error(ex.getMessage())
                .build();
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handleExceptions(Exception ex) {
//        log.error("Exception : {} | {}", ex.getMessage(), ex.getStackTrace());
//        ExceptionDto exceptionDto = ExceptionDto
//                .builder()
//                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
//                .message("Something Went Wrong")
//                .error(ex.getLocalizedMessage())
//                .build();
//        return new ResponseEntity<>(exceptionDto, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
