package com.app.backend.services.impl.drive;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Profile("gd")
public class SecretService {
    public static final String ENCRYPT_ALGO = "AES";
    private final String key;

    public SecretService(@Value("${app.vstream.secret}") String key) {
        this.key = key;
    }

    public byte[] encrypt(String text) {
        try {
            SecretKeySpec aesKey = new SecretKeySpec(key.getBytes(), ENCRYPT_ALGO);
            var cipher = Cipher.getInstance(ENCRYPT_ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            return cipher.doFinal(text.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Unable to encrypt.", e);
        }
    }

    public String decrypt(byte[] text) {
        try {
            SecretKeySpec aesKey = new SecretKeySpec(key.getBytes(), ENCRYPT_ALGO);
            var cipher = Cipher.getInstance(ENCRYPT_ALGO);
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            var decrypted = new String(cipher.doFinal(text));
            return decrypted;
        } catch (Exception e) {
            throw new RuntimeException("Unable to decrypt", e);
        }
    }
}
