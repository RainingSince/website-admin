package com.rainingsince.website.module.imageUpload;

import lombok.Data;

@Data
public class ImageEntity {

    public ImageEntity(String url) {
        this.url = url;
    }

    public ImageEntity() {
    }

    private String url;

}
