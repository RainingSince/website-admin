package com.rainingsince.website.module.tags.error;

import com.rainingsince.web.exception.ErrorEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TagsError implements ErrorEntity {
    USER_NAME_EXIT(10001, "标签名已存在");
    private int code;
    private String message;

}
