package com.app;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

/**
 * The entry point of the Spring Boot application.
 * <p>
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 */
@SpringBootApplication
@EnableCaching
@EnableScheduling
@Theme(value = "vstream", variant = Lumo.DARK)
@PWA(shortName = "homeflix", name = "homeflix", offlinePath = "offline.html", manifestPath = "sw.webmanifest")
@Slf4j
public class Application extends SpringBootServletInitializer implements AppShellConfigurator {
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Scheduled(fixedDelay = 10000L)
    void keepAlive() {
        try{
            var responseEntity = REST_TEMPLATE.getForEntity("https://vstream-lqcq.onrender.com/actuator/health", String.class);
            log.info("Service response code {}",responseEntity.getStatusCode());
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
    }

}
