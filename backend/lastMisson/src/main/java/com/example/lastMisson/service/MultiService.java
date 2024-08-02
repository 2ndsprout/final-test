package com.example.lastMisson.service;

import com.example.lastMisson.domain.Article;
import com.example.lastMisson.dtos.ArticleRequestDTO;
import com.example.lastMisson.dtos.ArticleResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MultiService {

    private final ArticleService articleService;

    private Long dateTimeTransfer(LocalDateTime dateTime) {

        if (dateTime == null) {
            return null;
        }
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    private ArticleResponseDTO articleResponseDTO (Article article) {
        return ArticleResponseDTO.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .createDate(this.dateTimeTransfer(article.getCreateDate()))
                .modifyDate(this.dateTimeTransfer(article.getModifyDate()))
                .build();
    }

    @Transactional
    public Page<ArticleResponseDTO> getArticlePage(int page) {
        Pageable pageable = PageRequest.of(page, 15);
        List<ArticleResponseDTO> articleResponseDTOList = new ArrayList<>();
        Page<Article> articlePage = this.articleService.getPage(pageable);
        for (Article article : articlePage) {
            articleResponseDTOList.add(this.articleResponseDTO(article));
        }
        return new PageImpl<>(articleResponseDTOList, pageable, articlePage.getTotalElements());
    }

    @Transactional
    public ArticleResponseDTO saveArticle (ArticleRequestDTO articleRequestDTO) {
        Article article = this.articleService.saveArticle(articleRequestDTO.getTitle(), articleRequestDTO.getContent());
        return this.articleResponseDTO(article);
    }

    @Transactional
    public ArticleResponseDTO updateArticle (ArticleRequestDTO articleRequestDTO) {
        Article _article = this.articleService.get(articleRequestDTO.getId());
        Article article = this.articleService.update(_article, articleRequestDTO.getTitle(), articleRequestDTO.getContent());
        return this.articleResponseDTO(article);
    }

    @Transactional
    public void deleteArticle (Long id) {
        Article article = this.articleService.get(id);
        this.articleService.delete(article);
    }

    @Transactional
    public ArticleResponseDTO getArticle(Long id) {
        Article article = this.articleService.get(id);
        return this.articleResponseDTO(article);
    }
}
