package com.app.backend.services;

import com.app.backend.controllers.VideoFile;
import org.springframework.core.io.Resource;
import reactor.core.publisher.Mono;

import java.util.List;

public interface VServiceInterface {
    List<VideoFile> videoLists();
    VideoFile getVideo(String identifier);
    Mono<Resource> getVideoStream(String fileName);
}
