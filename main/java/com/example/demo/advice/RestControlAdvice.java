package com.example.demo.advice;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.InsertFailException;
import com.example.demo.exception.ResourceAlreadyInUseException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.payload.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControlAdvice {

    private static final Logger logger = LoggerFactory.getLogger(RestControlAdvice.class);

    @ExceptionHandler(value = BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleBadRequestException(BadRequestException ex) {
        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName());
    }

    @ExceptionHandler(value = InsertFailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleInsertFailException(InsertFailException ex) {
        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName());
    }
    @ExceptionHandler(value = ResourceAlreadyInUseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleResourceAlreadyInUseException(ResourceAlreadyInUseException ex) {
        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName());
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleUserNotFoundException(UserNotFoundException ex) {
        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName());
    }
}
