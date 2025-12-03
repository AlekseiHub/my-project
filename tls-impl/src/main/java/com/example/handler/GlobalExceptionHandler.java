package com.example.handler;

import com.example.dto.response.ErrorResponse;
import com.example.dto.response.ErrorType;
import com.example.exceptions.AlreadyExistsException;
import com.example.exceptions.NotFoundCertificateResultException;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.webjars.NotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(NotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.from(ErrorType.NOT_FOUND));
    }

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<ErrorResponse> handleException(InternalException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.from(ErrorType.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.from(ErrorType.BAD_REQUEST));
    }

    @ExceptionHandler(NotFoundCertificateResultException.class)
    public ResponseEntity<ErrorResponse> handleException(NotFoundCertificateResultException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.from(ErrorType.NOT_FOUND));
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleException(AlreadyExistsException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.from(ErrorType.ALREADY_EXIST));
    }
}