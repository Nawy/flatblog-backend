package com.ermolaev.flatblog.model.dto;

import com.ermolaev.flatblog.model.component.Component;
import java.time.LocalDateTime;
import java.util.List;
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
  private List<Component> components;
  private LocalDateTime createDate;
}
