package com.ermolaev.flatblog.util;

import static org.springframework.web.reactive.function.server.ServerResponse.status;

import com.ermolaev.flatblog.exception.HttpException;
import com.ermolaev.flatblog.model.HttpErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public abstract class HttpErrorUtils {

  private HttpErrorUtils() {}

  public static Mono<ServerResponse> handleError(Throwable throwable) {
    if (throwable == null) {
      throw new IllegalStateException("Throwable is null");
    }
    try {
      HttpException exception = (HttpException) throwable;
      return status(exception.getStatus())
          .body(
              Mono.just(
                  new HttpErrorDto(
                      exception.getStatus().value(),
                      exception.getMessage()
                  )),
              HttpErrorDto.class
          );
    } catch (ClassCastException e) {
      return status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(
              Mono.just(
                  new HttpErrorDto(
                      HttpStatus.INTERNAL_SERVER_ERROR.value(),
                      throwable.toString()
                  )),
              HttpErrorDto.class
          );
    }
  }
}
