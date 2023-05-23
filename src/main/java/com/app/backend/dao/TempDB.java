package com.app.backend.dao;

import com.app.backend.VideoData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class TempDB {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final Map<String, VideoDAOData> movieMap = new HashMap<>();

    @SneakyThrows
    public TempDB(@Value("${app.vstream.videodb.path}") String videoDBFilePath) {
        var strings = FileUtils.readFileToString(new File(videoDBFilePath), StandardCharsets.UTF_8);
        Arrays.stream(OBJECT_MAPPER.readValue(strings, VideoDAOData[].class)).toList()
                .forEach(videoDAOData -> {
                    var dataIdentifier = videoDAOData.getIdentifier();
                    var key = dataIdentifier == null ? UUID.randomUUID().toString() : dataIdentifier;
                    movieMap.computeIfAbsent(key, integer -> videoDAOData.setIdentifier(key));
                });
    }

    public Map<String, VideoDAOData> getData() {
        return movieMap;
    }

    public void addData(VideoData videoData){
        movieMap.put(videoData.getIdentifier(), new ObjectMapper().convertValue(videoData, VideoDAOData.class));
    }

}
