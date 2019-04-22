package com.ermolaev.flatblog.mapper;

import com.ermolaev.flatblog.model.user.ArticleUser;
import com.ermolaev.flatblog.model.dto.CreateUserDto;
import com.ermolaev.flatblog.model.dto.UserDto;
import java.time.LocalDateTime;
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
    return new ArticleUser(
        null,
        userDto.getLogin(),
        passwordEncoder.encode(userDto.getPassword()),
        null,
        userDto.getName(),
        LocalDateTime.now()
    );
  }
}
