package com.ermolaev.flatblog.exception;

import org.springframework.http.HttpStatus;

public class BadRequestHttpException extends HttpException {

  public BadRequestHttpException(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }
}
