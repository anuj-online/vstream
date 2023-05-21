package com.app.backend.controllers;

import com.app.backend.services.VServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class VideoController {
    private final VServiceInterface service;

    public VideoController(VServiceInterface service) {
        this.service = service;
    }

    @GetMapping(value = "video/{title}", produces = {"video/mp4", "video/mov", "video/mkv", "video/flv", "video/m4v"})
    public Mono<Resource> getVideos(@PathVariable String title, @RequestHeader("Range") String range) {
        log.info("requesting range {}", range);
        var videoStream = service.getVideoStream(title);
        return videoStream;
    }
}
