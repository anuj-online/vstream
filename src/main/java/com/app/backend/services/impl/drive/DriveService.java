package com.app.backend.services.impl.drive;

import com.app.backend.controllers.VideoFile;
import com.app.backend.services.VServiceInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Profile("gd")
public class DriveService implements VServiceInterface {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public final Map<String, VideoFile> map = new HashMap<>();

    private final DownloadFile downloadFile;

    @SneakyThrows
    public DriveService(@Value("${app.vstream.mv}") String mvFileName, SecretService secretService, DownloadFile downloadFile) {
        var mvs = FileUtils.readFileToByteArray(new File(mvFileName));
        var decrypt = secretService.decrypt(mvs);
        OBJECT_MAPPER.readValue(decrypt, Movies.class).getMovies().forEach(movie -> {
            var videoFile = new VideoFile();
            videoFile.setName(movie.getName());
            videoFile.setIdentifier(movie.getId());
            videoFile.setFullPath(movie.getUrl());
            map.computeIfAbsent(videoFile.getIdentifier(), vf -> videoFile);
        });
        this.downloadFile = downloadFile;
    }

    public List<VideoFile> videoLists() {
        return map.values().stream().toList();
    }

    public VideoFile getVideo(String identifier) {
        return map.get(identifier);
    }

    @SneakyThrows
    public Mono<Resource> getVideoStream(String fileName) {
        return Mono.fromSupplier(() -> {
            try {
                return new ByteArrayResource(downloadFile.downloadFile(fileName).toByteArray());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

@Data
class Movies {
    private List<Movie> movies;
}

@Data
class Movie {
    private String name;
    private String id;
    private String url;
}
