package com.ermolaev.flatblog.mapper;

import com.ermolaev.flatblog.model.dto.CreateUserDto;
import com.ermolaev.flatblog.model.dto.UserDto;
import com.ermolaev.flatblog.model.security.Authorities;
import com.ermolaev.flatblog.model.user.ArticleUser;
import com.ermolaev.flatblog.model.user.Authority;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {

  private UserMapper(){}

  public static UserDto toDto(ArticleUser articleUser) {
    return new UserDto(
        articleUser.getId(),
        articleUser.getName(),
        articleUser.getCreateDate()
    );
  }

  public static ArticleUser fromDto(CreateUserDto userDto, PasswordEncoder passwordEncoder) {
    Set<Authority> authoritySet = new HashSet<>();
    authoritySet.add(new Authority(Authorities.ROLE_ADMIN.getName()));
    return new ArticleUser(
        null,
        userDto.getLogin(),
        passwordEncoder.encode(userDto.getPassword()),
        authoritySet,
        userDto.getName(),
        LocalDateTime.now()
    );
  }
}
