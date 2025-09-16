package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "http://localhost:5173")
public class ArticleController {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ArticleController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 返回所有文章的基本信息：id, folder_id, title
    @GetMapping("/summaries")
    public List<Map<String, Object>> getSummaries() {
        String sql = "SELECT id, folder_id, title FROM artitles ORDER BY id";
        return jdbcTemplate.queryForList(sql);
    }

    // 返回某一篇文章的内容
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getArticleContent(@PathVariable("id") Long id) {
        try {
            String sql = "SELECT id, content FROM artitles WHERE id = ?";
            Map<String, Object> row = jdbcTemplate.queryForMap(sql, id);
            return ResponseEntity.ok(row);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
