package com.ermolaev.flatblog.repository;

import com.ermolaev.flatblog.model.Article;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ArticleRepository extends ReactiveCrudRepository<Article, String> {

}
