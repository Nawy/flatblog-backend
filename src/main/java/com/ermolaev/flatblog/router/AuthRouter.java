package com.ermolaev.flatblog.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

import com.ermolaev.flatblog.exception.BadRequestHttpException;
import com.ermolaev.flatblog.model.dto.CredentialsDto;
import com.ermolaev.flatblog.model.security.JWToken;
import com.ermolaev.flatblog.service.security.JWTReactiveAuthenticationManager;
import com.ermolaev.flatblog.service.security.TokenProvider;
import com.ermolaev.flatblog.util.HttpErrorUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Configuration
public class AuthRouter {

  @Bean
  RouterFunction<ServerResponse> auth(JWTReactiveAuthenticationManager authenticationManager,
      TokenProvider tokenProvider) {
    return route(POST("/auth"), req ->
        req.bodyToMono(CredentialsDto.class)
            .map(credentials -> new UsernamePasswordAuthenticationToken(credentials.getLogin(),
                credentials.getPassword()))
            .flatMap(authenticationManager::authenticate)
            .doOnError(throwable -> new BadRequestHttpException("Bad credentials"))
            .doOnNext(ReactiveSecurityContextHolder::withAuthentication)
            .flatMap(authentication ->
                status(HttpStatus.ACCEPTED)
                .body(
                    Mono.just(new JWToken(tokenProvider.createToken(authentication))),
                    JWToken.class
                ))
            .onErrorResume(HttpErrorUtils::handleError)
    );
  }
}
