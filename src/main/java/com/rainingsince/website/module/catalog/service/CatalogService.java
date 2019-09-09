package com.rainingsince.website.module.catalog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainingsince.admin.user.entity.User;
import com.rainingsince.admin.user.error.UserError;
import com.rainingsince.admin.userRole.entity.UserRole;
import com.rainingsince.web.response.ResponseBuilder;
import com.rainingsince.website.module.catalog.entity.CatalogEntity;
import com.rainingsince.website.module.catalog.error.CatalogError;
import com.rainingsince.website.module.catalog.mapper.CatalogMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;

@Service
@Transactional
@AllArgsConstructor
public class CatalogService extends ServiceImpl<CatalogMapper, CatalogEntity> {

    @Override
    public boolean save(CatalogEntity entity) {
        entity.setId(IdWorker.getIdStr());
        return super.save(entity);
    }

    public IPage<CatalogEntity> pages(CatalogEntity catalog) {
        return page(catalog.toPage(), new QueryWrapper<>(catalog));
    }

    public ResponseEntity saveNotExit(CatalogEntity entity) {
        if (isExit(entity)) return ResponseBuilder.error(CatalogError.USER_NAME_EXIT);
        return ResponseBuilder.ok(save(entity));
    }

    public ResponseEntity updateNotExit(CatalogEntity entity) {
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

    public boolean isExit(CatalogEntity user) {
        CatalogEntity one = getOne(new QueryWrapper<CatalogEntity>()
                .eq("name", user.getName()));
        return one != null && !one.getId().equals(user.getId());
    }

}
