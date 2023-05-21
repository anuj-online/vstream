package com.app.backend.controllers;

import lombok.Data;

import java.util.UUID;

@Data
public class VideoFile {
    private String identifier;
    private String name;
    private long size;
    private boolean file;
    private String fullPath;
}