package com.ermolaev.flatblog.service;

import com.ermolaev.flatblog.model.UserArticleDetails;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserArticleDetailsService implements ReactiveUserDetailsService {

  private final UserService userService;

  @Override
  public Mono<UserDetails> findByUsername(String username) {
    if (StringUtils.isBlank(username)) {
      return Mono.empty();
    }
    return userService.findByLogin(username)
        .map(UserArticleDetails::new);
  }
}
