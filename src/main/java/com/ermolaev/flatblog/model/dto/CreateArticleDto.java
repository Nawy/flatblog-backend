package com.ermolaev.flatblog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateArticleDto {

  private String id;
  private String title;
  private String shortTitle;
  private String text;
}
