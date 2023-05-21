package com.app.backend.cloud;

import com.app.backend.VideoFile;
import com.app.backend.VideoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@Profile("gd")
public class GDriveCDNServiceLocal implements VideoService {
    private static String OUTPUT = "https://drive.google.com/uc?id=%s&authuser=2&export=download";
    public final Map<String, VideoFile> map = new HashMap<>();

    @SneakyThrows
    public GDriveCDNServiceLocal(@Value("${app.vstream.mvfile}") String mvFileName) {
        CompletableFuture.runAsync(() -> {
            try {
                var resource = new File(mvFileName);
                var file = Paths.get(resource.toURI()).toFile();
                new ObjectMapper().readValue((FileUtils.readFileToString(file).getBytes()), Movies.class)
                        .getMovies().forEach(movie -> {
                            var videoFile = new VideoFile();
                            videoFile.setName(movie.getName());
                            videoFile.setIdentifier(movie.getId());
                            videoFile.setFullPath(movie.getUrl());
                            map.computeIfAbsent(videoFile.getIdentifier(), vf -> videoFile);
                        });

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    @Cacheable("videoLists")
    public List<VideoFile> videoLists() {
        return map.values().stream().toList();
    }

    @Override
    public Mono<Resource> getVideoStream(String fileName) {
        var restTemplate = new RestTemplate();
        var byteArrayResourceResponseEntity = restTemplate.getForEntity(map.get(fileName).getFullPath(), ByteArrayResource.class);
        return Mono.fromSupplier(() -> byteArrayResourceResponseEntity.getBody());
    }

    @Override
    @Cacheable("identifier")
    public VideoFile getVideo(String identifier) {
        return map.get(identifier);
    }
}
