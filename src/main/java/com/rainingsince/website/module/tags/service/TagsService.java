package com.rainingsince.website.module.tags.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainingsince.web.response.ResponseBuilder;
import com.rainingsince.website.module.articleTags.entity.ArticleTagsEntity;
import com.rainingsince.website.module.articleTags.service.ArticleTagsService;
import com.rainingsince.website.module.tags.entity.TagsEntity;
import com.rainingsince.website.module.tags.error.TagsError;
import com.rainingsince.website.module.tags.mapper.TagsMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;

@Service
@Transactional
@AllArgsConstructor
public class TagsService extends ServiceImpl<TagsMapper, TagsEntity> {

    private ArticleTagsService articleTagsService;

    @Override
    public boolean save(TagsEntity entity) {
        entity.setId(IdWorker.getIdStr());
        return super.save(entity);
    }

    public IPage<TagsEntity> pages(TagsEntity catalog) {
        return page(catalog.toPage(), new QueryWrapper<>(catalog));
    }

    public ResponseEntity saveNotExit(TagsEntity entity) {
        if (isExit(entity)) return ResponseBuilder.error(TagsError.USER_NAME_EXIT);
        return ResponseBuilder.ok(save(entity));
    }

    public ResponseEntity updateNotExit(TagsEntity entity) {
        if (isExit(entity)) return ResponseBuilder.error(TagsError.USER_NAME_EXIT);
        return ResponseBuilder.ok(updateById(entity));
    }

    @Override
    public boolean removeById(Serializable id) {
        articleTagsService.remove(new QueryWrapper<ArticleTagsEntity>()
                .eq("tag_id", id));
        return super.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        articleTagsService.remove(new QueryWrapper<ArticleTagsEntity>()
                .in("tag_id", idList));
        return super.removeByIds(idList);
    }

    public boolean isExit(TagsEntity user) {
        TagsEntity one = getOne(new QueryWrapper<TagsEntity>()
                .eq("name", user.getName()));
        return one != null && !one.getId().equals(user.getId());
    }

}
