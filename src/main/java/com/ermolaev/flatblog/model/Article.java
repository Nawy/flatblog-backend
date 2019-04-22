package com.ermolaev.flatblog.model;

import com.ermolaev.flatblog.model.user.ArticleUser;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "article")
public class Article {

  @Id
  private String id;
  private String title;
  private String shortTitle;
  private ArticleUser user;
  private String text;
  private LocalDateTime updateDate;
  private LocalDateTime createDate;
}
