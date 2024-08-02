package com.example.lastMisson.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleResponseDTO {

    private Long id;

    private String title;

    private String content;

    private Long createDate;

    private Long modifyDate;

    @Builder
    public ArticleResponseDTO (Long id, String title, String content, Long createDate, Long modifyDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }
}
