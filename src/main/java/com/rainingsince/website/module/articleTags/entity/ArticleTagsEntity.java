package com.rainingsince.website.module.articleTags.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.rainingsince.mybatis.entity.BaseData;
import lombok.Data;

@Data
@TableName("tb_article_tags")
public class ArticleTagsEntity extends BaseData {

    private String articleId;
    private String tagId;

}
