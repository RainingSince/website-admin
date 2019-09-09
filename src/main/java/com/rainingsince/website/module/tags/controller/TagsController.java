package com.rainingsince.website.module.tags.controller;

import com.rainingsince.web.response.ResponseBuilder;
import com.rainingsince.website.module.tags.entity.TagsEntity;
import com.rainingsince.website.module.tags.service.TagsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("tags")
@AllArgsConstructor
public class TagsController {

    private TagsService tagsService;

    @GetMapping("/list")
    public ResponseEntity getAllRoles() {
        return ResponseBuilder.ok(tagsService.list());
    }

    @GetMapping()
    public ResponseEntity getRolesPage(TagsEntity tags) {
        return ResponseBuilder.ok(tagsService.pages(tags));
    }

    @PostMapping()
    public ResponseEntity insertRole(@RequestBody TagsEntity tags) {
        return tagsService.saveNotExit(tags);
    }

    @PutMapping
    public ResponseEntity updateRole(@RequestBody TagsEntity tags) {
        return tagsService.updateNotExit(tags);
    }


    @DeleteMapping("/{tagsId}")
    public ResponseEntity deleteRole(@PathVariable String tagsId) {
        return ResponseBuilder.ok(tagsService.removeById(tagsId));
    }

    @DeleteMapping("/select")
    public ResponseEntity deleteRoles(@RequestBody List<String> ids) {
        return ResponseBuilder.ok(tagsService.removeByIds(ids));
    }


}
