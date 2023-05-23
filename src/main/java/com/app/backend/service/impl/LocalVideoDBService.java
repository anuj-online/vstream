package com.app.backend.service.impl;

import com.app.backend.dao.VideoDB;
import com.app.backend.service.VAccess;
import com.app.backend.service.VideoFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("localdb")
@Slf4j
public class LocalVideoDBService extends DBServiceInterface implements VAccess {
    private final VideoDB videoDB;

    public LocalVideoDBService(VideoDB videoDB) {
        super(videoDB);
        log.info("NewVideoService in action");
        this.videoDB = videoDB;
    }

    @Override
    public List<VideoFile> videoList() {
        return super.videoLists();
    }

    @Override
    public String getDisplayName(String identifier) {
        return super.getDisplayName(identifier);
    }

    @Override
    public ResponseEntity<Resource> getVideoStream(String identifier, String range) {
        return ResponseEntity.ok()
                .body(new FileSystemResource(videoDB.getMovie(identifier).getPath()));

    }
}
