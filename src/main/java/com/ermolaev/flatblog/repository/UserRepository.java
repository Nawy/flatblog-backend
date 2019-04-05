package com.ermolaev.flatblog.repository;

import com.ermolaev.flatblog.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, String> {

  Mono<User> findByLogin(String login);
}
