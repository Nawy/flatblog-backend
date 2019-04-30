package com.ermolaev.flatblog.model.security;

import java.util.Collection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class UserAuthenticationToken extends UsernamePasswordAuthenticationToken {

  public UserAuthenticationToken(
      Object principal,
      Object credentials,
      Object articleUser,
      Collection<? extends GrantedAuthority> authorities
  ) {
    super(principal, credentials, authorities);
    this.setDetails(articleUser);
  }
}
