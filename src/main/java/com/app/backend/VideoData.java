package com.app.backend;

import lombok.Data;

@Data
public class VideoData {
    private int index;
    private String realFileName;
    private String format;
    private String path;
    private String displayName;
    private String quality;
    private String identifier;
}
