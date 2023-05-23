package com.app.backend;

import com.app.backend.dao.TempDB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    private final TempDB videoDB;

    public AdminController(TempDB videoDB) {
        this.videoDB = videoDB;
    }

    @PostMapping
    public ResponseEntity addVideo(@RequestBody VideoData videoData) {
        videoDB.addData(videoData);
        log.info("saved data");
        return ResponseEntity.ok().build();
    }
}
