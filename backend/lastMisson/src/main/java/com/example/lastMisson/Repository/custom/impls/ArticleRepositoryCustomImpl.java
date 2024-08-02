package com.example.lastMisson.Repository.custom.impls;

import com.example.lastMisson.Repository.custom.ArticleRepositoryCustom;
import com.example.lastMisson.domain.Article;
import com.example.lastMisson.domain.QArticle;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class ArticleRepositoryCustomImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QArticle qArticle = QArticle.article;

        @Override
        public Page<Article> getPages(Pageable pageable) {
            List<Article> articles = jpaQueryFactory.selectFrom(qArticle)
                    .orderBy(qArticle.createDate.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            long total = jpaQueryFactory.selectFrom(qArticle)
                    .fetchCount();

            return new PageImpl<>(articles, pageable, total);
        }

}
