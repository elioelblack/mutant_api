package com.mutant.api.exception.custom;

import com.mutant.api.exception.HttpException;
import org.springframework.http.HttpStatus;

public class InvalidNucleoidNameException extends HttpException {

  public InvalidNucleoidNameException() {
    super(HttpStatus.BAD_REQUEST, "DNA's nucleoid must be one of A, T, C, G");
  }
}