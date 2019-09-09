package com.rainingsince.website.module.tags.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rainingsince.website.module.tags.entity.TagsEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagsMapper extends BaseMapper<TagsEntity> {
}
