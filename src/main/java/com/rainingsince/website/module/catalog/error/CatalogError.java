package com.rainingsince.website.module.catalog.error;

import com.rainingsince.web.exception.ErrorEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CatalogError implements ErrorEntity {
    USER_NAME_EXIT(10001, "分类名已存在");
    private int code;
    private String message;

}
