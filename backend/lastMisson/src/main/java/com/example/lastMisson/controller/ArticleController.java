package com.example.lastMisson.controller;

import com.example.lastMisson.dtos.ArticleRequestDTO;
import com.example.lastMisson.dtos.ArticleResponseDTO;
import com.example.lastMisson.service.MultiService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleController {

    private final MultiService multiService;

    @PostMapping
    public ResponseEntity<?> saveArticle(@RequestBody ArticleRequestDTO articleRequestDTO) {
        try {
            ArticleResponseDTO articleResponseDTO = this.multiService.saveArticle(articleRequestDTO);
            return ResponseEntity.status(HttpStatus.OK).body(articleResponseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("찾을 수 없음");
        }
    }

    @GetMapping
    public ResponseEntity<?> getArticle(@RequestHeader("ArticleId") Long id) {
        try {
            ArticleResponseDTO articleResponseDTO = this.multiService.getArticle(id);
            return ResponseEntity.status(HttpStatus.OK).body(articleResponseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("찾을 수 없음");
        }
    }

    @PutMapping
    public ResponseEntity<?> updateArticle(@RequestBody ArticleRequestDTO articleRequestDTO) {
        try {
            ArticleResponseDTO articleResponseDTO = this.multiService.updateArticle(articleRequestDTO);
            return ResponseEntity.status(HttpStatus.OK).body(articleResponseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("찾을 수 없음");
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteArticle(@RequestHeader("ArticleId") Long id) {
        try {
            this.multiService.deleteArticle(id);
            return ResponseEntity.status(HttpStatus.OK).body("삭제 완료");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("찾을 수 없음");
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getPage (@RequestHeader(value = "Page", defaultValue = "0") int page) {
        try {
            Page<ArticleResponseDTO> articleResponseDTOS = this.multiService.getArticlePage(page);
            return ResponseEntity.status(HttpStatus.OK).body(articleResponseDTOS);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("찾을 수 없음");
        }
    }
}
