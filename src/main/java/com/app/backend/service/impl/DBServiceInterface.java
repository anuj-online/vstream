package com.app.backend.service.impl;

import com.app.backend.dao.VideoDB;
import com.app.backend.service.VideoFile;

import java.util.List;

public abstract class DBServiceInterface {
    private final VideoDB videoDB;

    public DBServiceInterface(VideoDB videoDB) {
        this.videoDB = videoDB;
    }

    List<VideoFile> videoLists() {
        return videoDB.getMovieData().stream().map(data -> {
            var videoFile = new VideoFile();
            videoFile.setName(data.getDisplayName());
            videoFile.setIdentifier(data.getIdentifier());
            return videoFile;
        }).toList();
    }

    String getDisplayName(String identifier) {
        return videoDB.getMovie(identifier).getDisplayName();
    }
}
