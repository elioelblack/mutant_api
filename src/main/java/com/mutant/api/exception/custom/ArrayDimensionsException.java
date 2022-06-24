package com.mutant.api.exception.custom;

import com.mutant.api.exception.HttpException;
import org.springframework.http.HttpStatus;

public class ArrayDimensionsException extends HttpException {

  public ArrayDimensionsException() {
    super(HttpStatus.BAD_REQUEST, "The imput array must be NxN");
  }
}