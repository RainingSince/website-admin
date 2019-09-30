package com.rainingsince.website.module.project.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainingsince.admin.user.service.UserService;
import com.rainingsince.web.response.ResponseBuilder;
import com.rainingsince.website.module.catalog.error.CatalogError;
import com.rainingsince.website.module.project.entity.ProjectEntity;
import com.rainingsince.website.module.project.mapper.ProjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;

@Service
@AllArgsConstructor
@Transactional
public class ProjectService extends ServiceImpl<ProjectMapper, ProjectEntity> {

    private UserService userService;

    @Override
    public ProjectEntity getById(Serializable id) {
        ProjectEntity articleEntity = super.getById(id);
        articleEntity.setAuthor(userService.getById(articleEntity.getCreateBy()).getName());
        return articleEntity;
    }

    @Override
    public boolean save(ProjectEntity entity) {
        entity.setId(IdWorker.getIdStr());
        return super.save(entity);
    }

    public IPage<ProjectEntity> pages(ProjectEntity catalog) {
        QueryWrapper<ProjectEntity> wrapper = new QueryWrapper<>();

        if (StringUtils.isNotEmpty(catalog.getName())) {
            wrapper.like("name", catalog.getName());
        }

        IPage<ProjectEntity> page;

        page = this.page(catalog.toPage(), wrapper);

        page.getRecords().forEach(item -> {
            item.setAuthor(userService.getById(item.getCreateBy()).getName());
        });
        return page;
    }

    public ResponseEntity saveNotExit(ProjectEntity entity) {
        if (isExit(entity)) return ResponseBuilder.error(CatalogError.USER_NAME_EXIT);
        return ResponseBuilder.ok(save(entity));
    }

    public ResponseEntity updateNotExit(ProjectEntity entity) {
        if (isExit(entity)) return ResponseBuilder.error(CatalogError.USER_NAME_EXIT);
        return ResponseBuilder.ok(updateById(entity));
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        return super.removeByIds(idList);
    }

    public boolean isExit(ProjectEntity user) {
        ProjectEntity one = getOne(new QueryWrapper<ProjectEntity>()
                .eq("name", user.getName()));
        return one != null && !one.getId().equals(user.getId());
    }

}
