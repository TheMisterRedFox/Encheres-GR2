package fr.eni.encheres.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FileUploadUtil {

    private static final Path UPLOAD_DIR = Paths.get("upload-dir", "images");

    public static void ensureUploadDirExists() {
        if (Files.notExists(UPLOAD_DIR)) {
            try {
                Files.createDirectories(UPLOAD_DIR);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String saveImage(MultipartFile file) {
        ensureUploadDirExists();

        try (InputStream inputStream = file.getInputStream()) {
            String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path targetLocation = UPLOAD_DIR.resolve(uniqueFilename);
            Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return "/images/" + uniqueFilename;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}