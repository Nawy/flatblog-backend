package com.ermolaev.flatblog.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public abstract class HttpException extends RuntimeException {

  protected HttpStatus status;
  protected String message;
}
