package com.app.backend.service.impl;

import com.app.backend.dao.VideoDB;
import com.app.backend.service.VAccess;
import com.app.backend.service.VideoFile;
import com.google.common.base.CharMatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@Profile("gdrive")
public class GDriveVideoDBService extends DBServiceInterface implements VAccess {
    private static final String gAccessUrl = "https://drive.google.com/uc?id=%s&authuser=2&export=download";
    public static final int BUFFER_SIZE_LIMIT = 3000000;
    public static final String CONTENT_RANGE_HEADER_KEY = "Content-Range";
    public static final CharMatcher CHAR_MATCHER = CharMatcher.inRange('0', '9');
    private final RestTemplate restTemplate;

    public GDriveVideoDBService(VideoDB videoDB) {
        super(videoDB);
        log.info("GDriveVideoDBService in action.");
        this.restTemplate = new RestTemplate();
    }

    @Override
    public List<VideoFile> videoList() {
        return super.videoLists();
    }

    @Override
    public String getDisplayName(String identifier) {
        return super.getDisplayName(identifier);
    }

    @Override
    public ResponseEntity<byte[]> getVideoStream(String identifier, String range) {
        range = minimizeRange(range);
        var headers = new HttpHeaders();
        headers.add("Range", range);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        var exchange = restTemplate.exchange(String.format(gAccessUrl, identifier), HttpMethod.GET, entity, byte[].class);

        var body = exchange.getBody();
        var exchangeHeaders = exchange.getHeaders();

        var httpHeaders = prepareResponseHeaders(exchangeHeaders);

        log.info("responding {} MB of data", (body.length/1000)/1000);
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).headers(httpHeaders).body(exchange.getBody());


    }

    private static HttpHeaders prepareResponseHeaders(HttpHeaders exchangeHeaders) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentLength(exchangeHeaders.getContentLength());
        httpHeaders.set(CONTENT_RANGE_HEADER_KEY, Objects.requireNonNull(exchangeHeaders.get(CONTENT_RANGE_HEADER_KEY)).get(0));
        return httpHeaders;
    }

    private static String minimizeRange(String range) {
        try{
            var s = CHAR_MATCHER.retainFrom(range);
            return range + (Integer.valueOf(s) + BUFFER_SIZE_LIMIT);
        }
        catch (Exception e){
            log.error("Error in minimizing the range", e);
        }
        return range;
    }
}
