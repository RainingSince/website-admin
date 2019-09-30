package com.rainingsince.website.module.project.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rainingsince.mybatis.entity.BaseData;
import lombok.Data;

@Data
@TableName("tb_project")
public class ProjectEntity extends BaseData {
    private String content;
    private String imageCover;
    private String imageList;
    private String name;

    @TableField(exist = false)
    private String author;
}
