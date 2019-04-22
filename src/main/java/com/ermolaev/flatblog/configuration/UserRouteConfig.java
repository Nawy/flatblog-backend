package com.ermolaev.flatblog.configuration;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

import com.ermolaev.flatblog.exception.BadRequestHttpException;
import com.ermolaev.flatblog.mapper.UserMapper;
import com.ermolaev.flatblog.model.dto.CreateUserDto;
import com.ermolaev.flatblog.model.dto.UserDto;
import com.ermolaev.flatblog.service.UserService;
import com.ermolaev.flatblog.util.HttpErrorUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Configuration
public class UserRouteConfig {

  @Bean
  RouterFunction<ServerResponse> getAllUsers(UserService userService) {
    return route(GET("/users"), req ->
        ok().body(userService.findAll().map(UserMapper::toDto), UserDto.class)
    );
  }

  @Bean
  RouterFunction<ServerResponse> createUser(UserService userService, PasswordEncoder passwordEncoder) {
    return route(POST("/users"), req ->
        req.bodyToMono(CreateUserDto.class)
            .map(userDto -> UserMapper.fromDto(userDto,passwordEncoder))
            .flatMap(userService::create)
            .map(UserMapper::toDto)
            .flatMap(newUser -> status(HttpStatus.CREATED).body(Mono.just(newUser), UserDto.class))
            .switchIfEmpty(Mono.error(new BadRequestHttpException("Empty request")))
            .onErrorResume(HttpErrorUtils::handleError)
    );
  }
}
