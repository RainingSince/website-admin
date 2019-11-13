package com.rainingsince.website.module.article.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainingsince.admin.user.service.UserService;
import com.rainingsince.web.response.ResponseBuilder;
import com.rainingsince.website.module.article.entity.ArticleEntity;
import com.rainingsince.website.module.article.mapper.ArticleMapper;
import com.rainingsince.website.module.articleTags.entity.ArticleTagsEntity;
import com.rainingsince.website.module.articleTags.service.ArticleTagsService;
import com.rainingsince.website.module.catalog.error.CatalogError;
import com.rainingsince.website.module.catalog.service.CatalogService;
import com.rainingsince.website.module.tags.entity.TagsEntity;
import com.rainingsince.website.module.tags.service.TagsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ArticleService extends
        ServiceImpl<ArticleMapper, ArticleEntity> {

    private ArticleTagsService articleTagsService;
    private TagsService tagsService;
    private CatalogService catalogService;
    private UserService userService;

    public Long saveArticleTags(String id, List<String> permissions) {
        return articleTagsService.saveArticleTags(id, permissions);
    }

    public IPage<ArticleEntity> list(ArticleEntity entity) {
        IPage<ArticleEntity> list;
        QueryWrapper<ArticleEntity> wrapper = new QueryWrapper<>();
        wrapper.select("id","name","remark","create_date","update_date","catalog_id","create_by");
        if ("hot".equals(entity.getType())) {
            wrapper.orderBy(true, false, "sort");
        } else {
            wrapper.orderBy(true, false, "update_date");
        }
        list = this.page(entity.toPage(), wrapper);
        return list;
    }

    @Override
    public ArticleEntity getById(Serializable id) {
        List<String> permissionList = articleTagsService.listTagsByArticleId(id);
        ArticleEntity articleEntity = super.getById(id);
        articleEntity.setAuthor(userService.getById(articleEntity.getCreateBy()).getName());
        articleEntity.setTagIds(permissionList);
        return articleEntity;
    }

    @Override
    public boolean save(ArticleEntity entity) {
        entity.setId(IdWorker.getIdStr());
        if (entity.getTagIds().size() > 0) {
            saveArticleTags(entity.getId(), entity.getTagIds());
        }
        return super.save(entity);
    }

    public IPage<ArticleEntity> pages(ArticleEntity catalog) {
        QueryWrapper<ArticleEntity> wrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(catalog.getCatalogId())) {
            wrapper.eq("catalog_id", catalog.getCatalogId());
        }
        if (StringUtils.isNotEmpty(catalog.getName())) {
            wrapper.like("name", catalog.getName());
        }
        wrapper.select("id","name","remark","create_date","update_date","catalog_id","create_by");

        wrapper.orderByDesc("update_date");
        IPage<ArticleEntity> page;
        if (StringUtils.isEmpty(catalog.getTagId())) {
            page = this.page(catalog.toPage(), wrapper);
        } else {
            page = baseMapper.getPageWithTag(catalog.toPage(), catalog.getTagId());
        }
        page.getRecords().forEach(item -> {
            item.setTagIds(articleTagsService.listTagsByArticleId(item.getId()));
            item.setTagList(item.getTagIds().stream()
                    .map(id -> tagsService.getById(id)).map(TagsEntity::getName).collect(Collectors.toList()));
            item.setCatalogName(catalogService.getById(item.getCatalogId()).getName());
            item.setAuthor(userService.getById(item.getCreateBy()).getName());
        });
        return page;
    }

    public ResponseEntity saveNotExit(ArticleEntity entity) {
        if (isExit(entity)) return ResponseBuilder.error(CatalogError.USER_NAME_EXIT);
        return ResponseBuilder.ok(save(entity));
    }

    public ResponseEntity updateNotExit(ArticleEntity entity) {
        if (isExit(entity)) return ResponseBuilder.error(CatalogError.USER_NAME_EXIT);
        saveArticleTags(entity.getId(), entity.getTagIds());
        return ResponseBuilder.ok(updateById(entity));
    }

    @Override
    public boolean removeById(Serializable id) {
        articleTagsService.remove(new QueryWrapper<ArticleTagsEntity>()
                .eq("article_id", id));
        return super.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        articleTagsService.remove(new QueryWrapper<ArticleTagsEntity>()
                .in("article_id", idList));
        return super.removeByIds(idList);
    }

    public boolean isExit(ArticleEntity user) {
        ArticleEntity one = getOne(new QueryWrapper<ArticleEntity>()
                .eq("name", user.getName()));
        return one != null && !one.getId().equals(user.getId());
    }


}
