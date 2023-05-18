package com.app.backend;

import lombok.Data;

import java.util.UUID;

@Data
public class VideoFile {
    private UUID identifier;
    private String name;
    private long size;
    private boolean file;
    private String fullPath;
}