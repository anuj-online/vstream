package com.app.backend;

import org.springframework.core.io.Resource;
import reactor.core.publisher.Mono;

import java.util.List;

public interface VideoService {
    List<VideoFile> videoLists();
    public Mono<Resource> getVideoStream(String fileName);
    public VideoFile getVideo(String identifier);
}
