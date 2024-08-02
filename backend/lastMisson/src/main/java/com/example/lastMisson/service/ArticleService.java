package com.example.lastMisson.service;

import com.example.lastMisson.Repository.ArticleRepository;
import com.example.lastMisson.domain.Article;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional
    public Article saveArticle (String title, String content) {
        Article article = Article.builder()
                .title(title)
                .content(content)
                .build();
        return this.articleRepository.save(article);
    }

    @Transactional
    public Article update (Article article, String title, String content) {
        article.setTitle(title);
        article.setContent(content);
        article.setModifyDate(LocalDateTime.now());
        return this.articleRepository.save(article);
    }

    @Transactional
    public void delete (Article article) {
        this.articleRepository.delete(article);
    }

    @Transactional
    public Article get (Long id) {
        return this.articleRepository.findById(id).orElse(null);
    }

    @Transactional
    public Page<Article> getPage (Pageable pageable) {
        return this.articleRepository.getPages(pageable);
    }

}
