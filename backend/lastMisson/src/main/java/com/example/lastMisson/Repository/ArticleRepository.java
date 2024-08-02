package com.example.lastMisson.Repository;

import com.example.lastMisson.Repository.custom.ArticleRepositoryCustom;
import com.example.lastMisson.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryCustom {
}
