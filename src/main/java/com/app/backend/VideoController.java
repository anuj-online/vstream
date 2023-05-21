package com.app.backend;

import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class VideoController {
    private final VideoService service;

    public VideoController(VideoService service) {
        this.service = service;
    }

    @GetMapping(value = "video/{title}", produces = {"video/mp4", "video/mov", "video/mkv", "video/flv", "video/m4v"})
    public Mono<Resource> getVideos(@PathVariable String title, @RequestHeader("Range") String range) {
        return service.getVideoStream(title);
    }
}
