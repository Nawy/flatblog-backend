package com.ermolaev.flatblog.service;

import com.ermolaev.flatblog.exception.BadRequestHttpException;
import com.ermolaev.flatblog.model.User;
import com.ermolaev.flatblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public Mono<User> findByLogin(String login) {
    return userRepository.findByLogin(login);
  }

  public Mono<User> create(User user) {
    return userRepository.findByLogin(user.getLogin())
        .flatMap(value -> Mono.error(new BadRequestHttpException("User " + user.getLogin() + " already existed!")))
        .switchIfEmpty(Mono.defer(() -> userRepository.save(user)))
        .cast(User.class);
  }

  public Flux<User> findAll() {
    return userRepository.findAll();
  }
}
