package com.rainingsince.website.module.article.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rainingsince.mybatis.entity.BaseData;
import lombok.Data;

import java.util.List;

@Data
@TableName("tb_article")
public class ArticleEntity extends BaseData {

    private String name;
    private String imageCover;
    private String catalogId;
    private String content;

    @TableField(exist = false)
    private String catalogName;
    @TableField(exist = false)
    private List<String> tagList;

}
