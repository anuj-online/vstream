package com.app.backend.services.impl.drive;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;


@Service
@Profile("gd")
class DownloadFile {
    private static final String APPLICATION_NAME = "temporal-tiger-386816";
    private final String cdt;
    @SneakyThrows
    public DownloadFile(SecretService secretService, @Value("${app.vstream.cdt}") String filePath) {
        this.cdt = getString(secretService, filePath);
    }
    public ByteArrayOutputStream downloadFile(String realFileId) throws IOException {

        var credentials = GoogleCredentials.fromStream(
                        getCredentialsStream())
                .createScoped(List.of(DriveScopes.DRIVE));
        var requestInitializer = new HttpCredentialsAdapter(
                credentials);

        var service = new Drive.Builder(new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                requestInitializer)
                .setApplicationName(APPLICATION_NAME)
                .build();

        try {
            var outputStream = new ByteArrayOutputStream();
            File content = new File();

            service.files().create(content);
            service.files().get(realFileId)
                    .executeMediaAndDownloadTo(outputStream);

            return outputStream;
        } catch (GoogleJsonResponseException e) {
            throw new RuntimeException("Failed to get the file.", e);
        }
    }
    @SneakyThrows
    private InputStream getCredentialsStream() {
        return IOUtils.toInputStream(cdt, StandardCharsets.UTF_8);
    }

    private String getString(SecretService secretService, String filePath) throws IOException {
        return secretService.decrypt(FileUtils.readFileToByteArray(new java.io.File(filePath)));
    }
}