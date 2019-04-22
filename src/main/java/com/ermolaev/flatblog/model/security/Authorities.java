package com.ermolaev.flatblog.model.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Authorities {
  ROLE_USER("ROLE_USER"),
  ROLE_ADMIN("ROLE_ADMIN"),
  ROLE_ANONYMOUS("ROLE_ANONYMOUS");

  private String name;
}
