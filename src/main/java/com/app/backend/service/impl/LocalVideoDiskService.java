package com.app.backend.service.impl;

import com.app.backend.service.VAccess;
import com.app.backend.service.VideoFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Component
@Profile("localdisk")
@Slf4j
public class LocalVideoDiskService implements VAccess {
    private static final Map<String, VideoFile> files = new HashMap<>();
    private static final List<String> videoFileFormats = List.of("mp4", "mov", "mkv", "flv", "m4v", "avi",
            "MP4", "MOV", "MKV", "FLV", "AVI");

    public LocalVideoDiskService(@Value("${com.app.vstream.video-path}") String videoFolder) {
        log.info("VideoService in action.");
        loadMovies(videoFolder);
    }

    @Cacheable("files")
    private static Map<String, VideoFile> getFiles() {
        return files;
    }

    @Cacheable("videos")
    private static List<VideoFile> getVideoFiles() {
        return getFiles().values().stream().sorted(Comparator.comparing(VideoFile::getName)).toList();
    }

    private static VideoFile createVideoFileObject(File file) {
        var videoFile = new VideoFile();
        videoFile.setName(file.getName());
        videoFile.setIdentifier(UUID.randomUUID().toString());
        videoFile.setSize((file.length() / 1000) / 1000);
        videoFile.setFile(file.isFile());
        videoFile.setFullPath(file.getAbsolutePath());
        return videoFile;
    }

    private void loadMovies(String videoFolder) {
        CompletableFuture.runAsync(() -> {
            log.info("scanning for files.");
            var videoDir = new File(videoFolder);
            setup(videoDir);
            log.info("Finished scanning files. Scanned total of {} videos", getFiles().size());
        });
    }

    public List<VideoFile> videoList() {
        return getVideoFiles();
    }

    @Override
    public String getDisplayName(String identifier) {
        return files.get(identifier).getName();
    }

    private void setup(File videoDir) {
        var files = FileUtils.listFiles(videoDir, videoFileFormats.toArray(String[]::new), true);
        files.stream().filter(file -> file.length() > 0).forEach(file -> {
            var videoFile = createVideoFileObject(file);
            LocalVideoDiskService.files.computeIfAbsent(videoFile.getIdentifier(), s -> videoFile);
        });
    }

    public ResponseEntity<Resource> getVideoStream(String identifier, String range) {
        return ResponseEntity.ok()
                .body(new FileSystemResource(files.get(identifier).getFullPath()));
    }
}