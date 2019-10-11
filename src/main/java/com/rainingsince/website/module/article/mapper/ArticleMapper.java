package com.rainingsince.website.module.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rainingsince.website.module.article.entity.ArticleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ArticleMapper extends BaseMapper<ArticleEntity> {

    @Select("select a.id,a.name,a.remark,a.create_date,a.update_date,a.catalog_id,a.create_by FROM tb_article a inner join tb_article_tags at on at.article_id = a.id WHERE  at.deleted = 0 and tag_id = '${tagId}'")
    IPage<ArticleEntity> getPageWithTag(IPage page, @Param("tagId") String tagId);

}
