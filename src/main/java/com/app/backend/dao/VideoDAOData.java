package com.app.backend.dao;

import lombok.experimental.Accessors;

@lombok.Data
@Accessors(chain = true)
public class VideoDAOData {
    private int index;
    private String realFileName;
    private String format;
    private String path;
    private String displayName;
    private String quality;
    private String identifier;

}