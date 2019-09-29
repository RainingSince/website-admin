package com.rainingsince.website.module.tags.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.rainingsince.mybatis.entity.BaseData;
import lombok.Data;

@Data
@TableName("tb_tags")
public class TagsEntity extends BaseData {
    private String name;
    private String catalogId;
}
