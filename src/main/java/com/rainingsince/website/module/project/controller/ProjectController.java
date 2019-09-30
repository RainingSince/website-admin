package com.rainingsince.website.module.project.controller;

import com.rainingsince.web.response.ResponseBuilder;
import com.rainingsince.website.module.project.entity.ProjectEntity;
import com.rainingsince.website.module.project.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("project")
@AllArgsConstructor
public class ProjectController {

    private ProjectService projectService;


    @GetMapping("/{projectId}")
    public ResponseEntity getRole(@PathVariable String projectId) {
        return ResponseBuilder.ok(projectService.getById(projectId));
    }


    @GetMapping()
    public ResponseEntity getRolesPage(ProjectEntity entity) {
        return ResponseBuilder.ok(projectService.pages(entity));
    }

    @PostMapping()
    public ResponseEntity insertRole(@RequestBody ProjectEntity entity) {
        return projectService.saveNotExit(entity);
    }

    @PutMapping
    public ResponseEntity updateRole(@RequestBody ProjectEntity entity) {
        return projectService.updateNotExit(entity);
    }


    @DeleteMapping("/{projectId}")
    public ResponseEntity deleteRole(@PathVariable String projectId) {
        return ResponseBuilder.ok(projectService.removeById(projectId));
    }

    @DeleteMapping("/select")
    public ResponseEntity deleteRoles(@RequestBody List<String> ids) {
        return ResponseBuilder.ok(projectService.removeByIds(ids));
    }
}
