package com.app.backend.dao;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Profile({"localdb", "gdrive"})
@Slf4j
public class VideoDB {
    private final TempDB tempDB;
    @SneakyThrows
    public VideoDB(TempDB tempDB) {
        this.tempDB = tempDB;

    }

    @Cacheable("movie")
    public Collection<VideoDAOData> getMovieData() {
        log.info("Cache not available, calling DB");
        return tempDB.getData().values();
    }

    @Cacheable("identifier")
    public VideoDAOData getMovie(String identifier) {
        return tempDB.getData().get(identifier);
    }
}
