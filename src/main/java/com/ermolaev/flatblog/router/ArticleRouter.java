package com.ermolaev.flatblog.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

import com.ermolaev.flatblog.mapper.ArticleMapper;
import com.ermolaev.flatblog.model.UserArticleDetails;
import com.ermolaev.flatblog.model.dto.ArticleDto;
import com.ermolaev.flatblog.model.dto.CreateArticleDto;
import com.ermolaev.flatblog.model.dto.CreateUserDto;
import com.ermolaev.flatblog.model.dto.UserDto;
import com.ermolaev.flatblog.repository.ArticleRepository;
import com.ermolaev.flatblog.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Configuration
public class ArticleRouter {

  Mono<UserArticleDetails> auth() {
    return ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .map(Authentication::getPrincipal)
        .cast(UserArticleDetails.class)
        .switchIfEmpty(Mono.defer(() -> Mono.error(new RuntimeException())));
  }

  @Bean
  RouterFunction<ServerResponse> createNote(ArticleRepository articleRepository) {
    return route(POST("/articles"), req -> req.bodyToMono(CreateArticleDto.class)
        .zipWith(auth())
        .map(tuple -> ArticleMapper.fromCreateDto(tuple.getT1(), tuple.getT2().getArticleUser()))
        .flatMap(articleRepository::save)
        .map(ArticleMapper::toDto)
        .flatMap(article -> status(HttpStatus.CREATED).body(Mono.just(article), ArticleDto.class))
    );
  }
}
