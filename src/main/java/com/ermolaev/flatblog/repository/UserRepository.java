package com.ermolaev.flatblog.repository;

import com.ermolaev.flatblog.model.user.ArticleUser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<ArticleUser, String> {

  Mono<ArticleUser> findByLogin(String login);
}
