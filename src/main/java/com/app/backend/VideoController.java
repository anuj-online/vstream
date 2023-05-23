package com.app.backend;

import com.app.backend.service.VAccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class VideoController {
    private final VAccess service;

    public VideoController(VAccess service) {
        this.service = service;
    }

    @GetMapping(value = "video/{identifier}", produces = {"video/mp4", "video/mov", "video/mkv", "video/flv", "video/m4v"})
    public Mono<ResponseEntity<?>> getVideos(@PathVariable String identifier, @RequestHeader("Range") String range) {
        log.info("Range {}", range);
        return Mono.fromSupplier(() -> service.getVideoStream(identifier, range));
    }
}
