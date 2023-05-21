package com.app.backend.local;

import com.app.backend.VideoFile;
import com.app.backend.VideoService;
import com.vaadin.flow.router.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.File;
import java.net.MalformedURLException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
@Profile("!gd")
public class LocalVideoService implements VideoService {
    private static final Map<String, VideoFile> files = new HashMap<>();
    private static final List<String> videoFileFormats = List.of("mp4", "mov", "mkv", "flv", "m4v",
            "MP4", "MOV", "MKV", "FLV");

    public LocalVideoService(@Value("${com.app.vstream.video-path}") String videoFolder) {
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
        videoFile.setIdentifier(UUID.randomUUID().toString());
        videoFile.setName(file.getName());
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

    @Override
    public List<VideoFile> videoLists() {
        return getVideoFiles();
    }

    @Override
    public VideoFile getVideo(String identifier) {
        if (getFiles().containsKey(identifier)) return getFiles().get(identifier);
        throw new NotFoundException();
    }

    private void setup(File videoDir) {
        var files = FileUtils.listFiles(videoDir, videoFileFormats.toArray(String[]::new), true);
        files.stream().filter(file -> file.length() > 0).forEach(file -> {
            var videoFile = createVideoFileObject(file);
            LocalVideoService.files.computeIfAbsent(videoFile.getIdentifier().toString(), s -> videoFile);
        });
    }

    @Override
    public Mono<Resource> getVideoStream(String fileName) {
        return Mono.fromSupplier(() -> {
            try {
                return new FileUrlResource(getFiles().get(fileName).getFullPath());
            } catch (MalformedURLException e) {
                log.error("malformed url. ", e);
                throw new RuntimeException("Unable to play Video file");
            }
        });
    }
}