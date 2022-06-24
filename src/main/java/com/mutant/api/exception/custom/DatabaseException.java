package com.mutant.api.exception.custom;

import com.mutant.api.exception.HttpException;
import org.springframework.http.HttpStatus;

public class DatabaseException extends HttpException {

  public DatabaseException(String message) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, message);
  }
}