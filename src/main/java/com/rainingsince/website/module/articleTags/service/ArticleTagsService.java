package com.rainingsince.website.module.articleTags.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainingsince.website.module.articleTags.entity.ArticleTagsEntity;
import com.rainingsince.website.module.articleTags.mapper.ArticleTagsMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ArticleTagsService
        extends ServiceImpl<ArticleTagsMapper, ArticleTagsEntity> {


    @Override
    public boolean save(ArticleTagsEntity entity) {
        entity.setId(IdWorker.getIdStr());
        return super.save(entity);
    }

    public Long saveArticleTags(Serializable articleId, List<String> tags) {
        return tags.stream().map(id -> {
            ArticleTagsEntity insert = new ArticleTagsEntity();
            insert.setTagId(id);
            insert.setArticleId(articleId.toString());
            return save(insert);
        }).count();
    }

    public List<String> listTagsByArticleId(Serializable articleId) {
        return list(new QueryWrapper<ArticleTagsEntity>().eq("article_id", articleId))
                .stream().map(ArticleTagsEntity::getTagId).collect(Collectors.toList());
    }


    public List<String> listArticlesByTags(List<String> tags) {
        return list(new QueryWrapper<ArticleTagsEntity>().in("tag_id", tags))
                .stream().map(ArticleTagsEntity::getTagId).collect(Collectors.toList());
    }

    public List<String> listTagsByArticleIds(List<String> articleIds) {
        return list(new QueryWrapper<ArticleTagsEntity>().in("article_id", articleIds))
                .stream().map(ArticleTagsEntity::getTagId).collect(Collectors.toList());
    }

}
