package com.app;

import com.app.backend.services.impl.drive.DriveService;
import com.app.backend.services.impl.drive.SecretService;
import com.app.backend.services.VServiceInterface;
import com.app.backend.services.impl.local.VideoService;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

/**
 * The entry point of the Spring Boot application.
 * <p>
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 */
@SpringBootApplication
@EnableCaching
@Theme(value = "vstream", variant = Lumo.LIGHT)
@PWA(shortName = "homeflix", name="homeflix", offlinePath = "offline.html", manifestPath = "sw.webmanifest")
public class Application extends SpringBootServletInitializer implements AppShellConfigurator {
    public static void main(String[] args) throws IOException {
        Application.class.getClassLoader().getResources("");
        SpringApplication.run(Application.class, args);
    }
}
