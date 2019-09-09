package com.rainingsince.website.module.catalog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.rainingsince.mybatis.entity.BaseData;
import lombok.Data;

@Data
@TableName("tb_catalog")
public class CatalogEntity  extends BaseData {
    private String name;
}
