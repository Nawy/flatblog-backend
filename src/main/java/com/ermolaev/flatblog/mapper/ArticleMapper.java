package com.ermolaev.flatblog.mapper;

import static org.apache.commons.lang3.ObjectUtils.firstNonNull;

import com.ermolaev.flatblog.model.Article;
import com.ermolaev.flatblog.model.user.ArticleUser;
import com.ermolaev.flatblog.model.dto.ArticleDto;
import com.ermolaev.flatblog.model.dto.CreateArticleDto;
import java.time.LocalDateTime;

public class ArticleMapper {

  private ArticleMapper() {
  }

  public static ArticleDto toDto(Article article) {
    return new ArticleDto(
        article.getId(),
        article.getTitle(),
        article.getShortTitle(),
        UserMapper.toDto(article.getUser()),
        article.getText(),
        article.getCreateDate()
    );
  }

  public static Article fromCreateDto(CreateArticleDto article, ArticleUser user) {
    LocalDateTime now = LocalDateTime.now();
    return new Article(
        null,
        article.getTitle(),
        article.getShortTitle(),
        user,
        article.getText(),
        now,
        now
    );
  }

  public static Article fromUpdateDto(CreateArticleDto articleDto, Article existedArticle) {
    LocalDateTime now = LocalDateTime.now();
    return new Article(
        existedArticle.getId(),
        firstNonNull(articleDto.getTitle(), existedArticle.getTitle()),
        firstNonNull(articleDto.getShortTitle(), existedArticle.getShortTitle()),
        existedArticle.getUser(),
        firstNonNull(articleDto.getText(), existedArticle.getText()),
        now,
        now
    );
  }
}
