package com.rainingsince.website.module.article.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainingsince.web.response.ResponseBuilder;
import com.rainingsince.website.module.article.entity.ArticleEntity;
import com.rainingsince.website.module.article.mapper.ArticleMapper;
import com.rainingsince.website.module.articleTags.entity.ArticleTagsEntity;
import com.rainingsince.website.module.articleTags.service.ArticleTagsService;
import com.rainingsince.website.module.catalog.error.CatalogError;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ArticleService extends
        ServiceImpl<ArticleMapper, ArticleEntity> {

    private ArticleTagsService articleTagsService;

    public Long saveArticleTags(String id, List<String> permissions) {
        return articleTagsService.saveArticleTags(id, permissions);
    }


    @Override
    public ArticleEntity getById(Serializable id) {
        List<String>  permissionList = articleTagsService.listTagsByArticleId(id);
        ArticleEntity articleEntity = super.getById(id);
        articleEntity.setTagList(permissionList);
        return articleEntity;
    }

    @Override
    public boolean save(ArticleEntity entity) {
        entity.setId(IdWorker.getIdStr());
        return super.save(entity);
    }

    public IPage<ArticleEntity> pages(ArticleEntity catalog) {
        return page(catalog.toPage(), new QueryWrapper<>(catalog));
    }

    public ResponseEntity saveNotExit(ArticleEntity entity) {
        if (isExit(entity)) return ResponseBuilder.error(CatalogError.USER_NAME_EXIT);
        return ResponseBuilder.ok(save(entity));
    }

    public ResponseEntity updateNotExit(ArticleEntity entity) {
        if (isExit(entity)) return ResponseBuilder.error(CatalogError.USER_NAME_EXIT);
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
