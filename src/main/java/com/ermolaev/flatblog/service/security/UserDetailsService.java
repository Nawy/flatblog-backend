package com.ermolaev.flatblog.service.security;

import com.ermolaev.flatblog.model.UserArticleDetails;
import com.ermolaev.flatblog.model.user.ArticleUser;
import com.ermolaev.flatblog.repository.UserRepository;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserDetailsService implements ReactiveUserDetailsService {

  private final UserRepository userRepository;

  @Override
  public Mono<UserDetails> findByUsername(String login) {
    if (StringUtils.isBlank(login)) {
      return Mono.empty();
    }
    return userRepository.findByLogin(login)
        .filter(Objects::nonNull)
        .switchIfEmpty(Mono.error(new BadCredentialsException(String.format("ArticleUser %s not found in database", login))))
        .map(this::createUser);
  }

  public UserArticleDetails createUser(ArticleUser user) {
    Set<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
        .map(authority -> new SimpleGrantedAuthority(authority.getName()))
        .collect(Collectors.toSet());
    return new UserArticleDetails(
        user,
        grantedAuthorities
    );
  }
}
