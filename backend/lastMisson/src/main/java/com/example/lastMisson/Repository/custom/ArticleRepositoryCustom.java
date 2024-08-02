package com.example.lastMisson.Repository.custom;

import com.example.lastMisson.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepositoryCustom {

    Page<Article> getPages (Pageable pageable);
}
