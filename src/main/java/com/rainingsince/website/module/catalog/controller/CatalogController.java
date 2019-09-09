package com.rainingsince.website.module.catalog.controller;

import com.rainingsince.web.response.ResponseBuilder;
import com.rainingsince.website.module.catalog.entity.CatalogEntity;
import com.rainingsince.website.module.catalog.service.CatalogService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("catalog")
@AllArgsConstructor
public class CatalogController {

    private CatalogService catalogService;

    @GetMapping("/list")
    public ResponseEntity getAllRoles() {
        return ResponseBuilder.ok(catalogService.list());
    }

    @GetMapping()
    public ResponseEntity getRolesPage(CatalogEntity catalog) {
        return ResponseBuilder.ok(catalogService.pages(catalog));
    }

    @PostMapping()
    public ResponseEntity insertRole(@RequestBody CatalogEntity catalog) {
        return catalogService.saveNotExit(catalog);
    }

    @PutMapping
    public ResponseEntity updateRole(@RequestBody CatalogEntity catalog) {
        return catalogService.updateNotExit(catalog);
    }


    @DeleteMapping("/{catalogId}")
    public ResponseEntity deleteRole(@PathVariable String catalogId) {
        return ResponseBuilder.ok(catalogService.removeById(catalogId));
    }

    @DeleteMapping("/select")
    public ResponseEntity deleteRoles(@RequestBody List<String> ids) {
        return ResponseBuilder.ok(catalogService.removeByIds(ids));
    }


}
