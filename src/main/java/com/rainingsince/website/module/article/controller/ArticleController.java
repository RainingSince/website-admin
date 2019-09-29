package com.rainingsince.website.module.article.controller;

import com.rainingsince.web.response.ResponseBuilder;
import com.rainingsince.website.module.article.entity.ArticleEntity;
import com.rainingsince.website.module.article.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@Controller
@RequestMapping("article")
@AllArgsConstructor
public class ArticleController {

    private ArticleService articleService;

    @GetMapping("/list")
    public ResponseEntity getAllRoles(ArticleEntity entity) {
        return ResponseBuilder.ok(articleService.list(entity));
    }


    @GetMapping("/{articleId}")
    public ResponseEntity getRole(@PathVariable String articleId) {
        return ResponseBuilder.ok(articleService.getById(articleId));
    }


    @GetMapping()
    public ResponseEntity getRolesPage(ArticleEntity entity) {
        return ResponseBuilder.ok(articleService.pages(entity));
    }

    @PutMapping("/{articleId}/select/tags")
    public ResponseEntity updatePermissionByRole(@PathVariable String articleId,
                                                 @RequestBody List<String> tags) {
        return ResponseBuilder.ok(articleService.saveArticleTags(articleId, tags));
    }

    @PostMapping()
    public ResponseEntity insertRole(@RequestBody ArticleEntity entity) {
        return articleService.saveNotExit(entity);
    }

    @PutMapping
    public ResponseEntity updateRole(@RequestBody ArticleEntity entity) {
        return articleService.updateNotExit(entity);
    }


    @DeleteMapping("/{articleId}")
    public ResponseEntity deleteRole(@PathVariable String articleId) {
        return ResponseBuilder.ok(articleService.removeById(articleId));
    }

    @DeleteMapping("/select")
    public ResponseEntity deleteRoles(@RequestBody List<String> ids) {
        return ResponseBuilder.ok(articleService.removeByIds(ids));
    }

}
