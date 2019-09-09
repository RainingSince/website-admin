package com.rainingsince.website.module.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rainingsince.website.module.article.entity.ArticleEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<ArticleEntity> {
}
