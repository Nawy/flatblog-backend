package com.ermolaev.flatblog.mapper;

import com.ermolaev.flatblog.model.User;
import com.ermolaev.flatblog.model.dto.CreateUserDto;
import com.ermolaev.flatblog.model.dto.UserDto;
import java.time.LocalDateTime;

public class UserMapper {

  private UserMapper(){}

  public static UserDto toDto(User user) {
    return new UserDto(
        user.getId(),
        user.getName(),
        user.getCreateDate()
    );
  }

  public static User fromDto(CreateUserDto userDto) {
    return new User(
        null,
        userDto.getName(),
        userDto.getLogin(),
        userDto.getPassword(),
        LocalDateTime.now()
    );
  }
}
