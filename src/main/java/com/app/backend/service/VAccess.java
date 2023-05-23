package com.app.backend.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VAccess {

    @Cacheable("video-list")
    List<VideoFile> videoList();

    @Cacheable("display-name")
    String getDisplayName(String identifier);

    ResponseEntity<?> getVideoStream(String fileName, String range);
}
