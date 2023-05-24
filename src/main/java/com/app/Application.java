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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

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
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Scheduled(fixedDelay = 10000L)
    void keepAlive() {
        var runtime = Runtime.getRuntime();
        log.info("Keeping alive at \n |Time: {}| \n |currentThread{}| \n |activeCount{}| \n |processors {}| \n |freeMemory{}MB| \n |maxMemory {}MB|",
                LocalDateTime.now(),
                Thread.currentThread().getName(),
                Thread.activeCount(),
                runtime.availableProcessors(),
                getMemoryInMB(runtime.freeMemory()),
                getMemoryInMB(runtime.maxMemory()));
    }

    private static double getMemoryInMB(long runtime) {
        return runtime / (double) (1024 * 1024);
    }

}
