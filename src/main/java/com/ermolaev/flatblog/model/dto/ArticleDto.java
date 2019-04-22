package com.ermolaev.flatblog.model.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ArticleDto {

  private String id;
  private String title;
  private String shortTitle;
  private UserDto user;
  private String text;
  private LocalDateTime createDate;
}
