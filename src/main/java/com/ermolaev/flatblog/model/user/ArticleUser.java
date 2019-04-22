package com.ermolaev.flatblog.model.user;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class ArticleUser {

  @Id
  private String id;
  @Indexed(unique = true)
  private String login;
  private String password;
  private Set<Authority> authorities = new HashSet<>();
  private String name;
  private LocalDateTime createDate;

  public ArticleUser(String login, Set<Authority> authorities) {
    this.login = login;
    this.authorities = authorities;
  }
}
