package com.mutant.api.exception;

import com.mutant.api.model.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HttpExceptionHandler {

  @ExceptionHandler
  public ResponseEntity<ErrorResponseDto> handleHttpException(HttpException e) {
    return new ResponseEntity<>(new ErrorResponseDto(e.getMessage()), e.getHttpStatus());
  }
}