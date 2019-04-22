package com.ermolaev.flatblog.service;

import com.ermolaev.flatblog.exception.BadRequestHttpException;
import com.ermolaev.flatblog.model.user.ArticleUser;
import com.ermolaev.flatblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public Mono<ArticleUser> findByLogin(String login) {
    return userRepository.findByLogin(login);
  }

  public Mono<ArticleUser> create(ArticleUser articleUser) {
    return userRepository.findByLogin(articleUser.getLogin())
        .flatMap(value -> Mono.error(new BadRequestHttpException("ArticleUser " + articleUser.getLogin() + " already existed!")))
        .switchIfEmpty(Mono.defer(() -> userRepository.save(articleUser)))
        .cast(ArticleUser.class);
  }

  public Flux<ArticleUser> findAll() {
    return userRepository.findAll();
  }
}
