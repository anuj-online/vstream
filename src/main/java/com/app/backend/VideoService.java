package com.app.backend;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.File;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
@Slf4j
public class VideoService {
    private static final Map<String, VideoFile> files = new HashMap<>();

    @Cacheable("files")
    private static Map<String, VideoFile> getFiles(){
        return files;
    }

    private static final List<String> videoFileFormats = List.of("mp4", "mov", "mkv", "flv", "m4v");

    public VideoService(@Value("${com.app.vstream.video-path}") String videoFolder) {
        loadMovies(videoFolder);
    }

    private void loadMovies(String videoFolder) {
        var voidCompletableFuture = CompletableFuture.runAsync(() -> {
            log.info("scanning for files.");
            var videoFiles = getVideoFiles(new File(videoFolder));
            videoFiles.forEach(videoFile -> getFiles().computeIfAbsent(videoFile.getIdentifier().toString(), s -> videoFile));
            log.info("Finished scanning files. Scanned total of {} videos", videoFiles.size());
        });
    }

    public Set<VideoFile> videoLists() {
        return new HashSet<>(getFiles().values()).stream().sorted(Comparator.comparing(VideoFile::getName)).collect(Collectors.toCollection(LinkedHashSet::new));
    }
    public VideoFile getVideo(String identifier){
        return getFiles().get(identifier);
    }

    private Set<VideoFile> getVideoFiles(File videoDir) {
        Set<VideoFile> videoLists = new HashSet<>();
        addFiles(videoDir, videoLists);
        return videoLists;
    }

    private void addFiles(File videoDir, Set<VideoFile> videoLists) {
        for (File file : (Objects.requireNonNull(videoDir.listFiles()))) {
            if (file.isFile() && isVideoFile(file)) {
                var videoFile = new VideoFile();
                videoFile.setIdentifier(UUID.randomUUID());
                videoFile.setName(file.getName());
                videoFile.setSize((file.length() / 1000) / 1000);
                videoFile.setFile(file.isFile());
                videoFile.setFullPath(file.getAbsolutePath());
                videoLists.add(videoFile);
            } else {
                if (file.isDirectory()) {
                    addFiles(file.getAbsoluteFile(), videoLists);
                }
            }
        }
    }

    private boolean isVideoFile(File file) {
        var name = file.getName();
        return videoFileFormats.contains(FileNameUtils.getExtension(name.contains(".") ? name : "").toLowerCase());
    }

    public Mono<Resource> getVideoStream(String fileName) {
        return Mono.fromSupplier(() -> {
            var file = new File(getFiles().get(fileName).getFullPath());
            return new FileSystemResource(file.getAbsolutePath());
        });
    }
}